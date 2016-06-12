package org.brjia.qs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.brjia.qs.mapper.AreaRegDTOMapper;
import org.brjia.qs.mapper.FamilyRegDTOMapper;
import org.brjia.qs.mapper.MemberRegDTOMapper;
import org.brjia.qs.mapper.UserRegDTOMapper;
import org.brjia.qs.model.AreaRegDTO;
import org.brjia.qs.model.AreaRegDTOExample;
import org.brjia.qs.model.FamilyRegDTO;
import org.brjia.qs.model.FamilyRegDTOExample;
import org.brjia.qs.model.MemberRegDTO;
import org.brjia.qs.model.MemberRegDTOExample;
import org.brjia.qs.model.UserRegDTO;
import org.brjia.qs.model.UserRegDTOExample;
import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.round.UploadInfo;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.UserInfo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
/**
 * 用户服务
 * 一个用户关联一个倩女角色，新注册用户默认关联匿名用户
 * TODO 别人家用户可以关联任意多个倩女角色，但只有一个主角色
 * 
 * @author hujiawei
 *
 */
@Service
public class UserService {
	
	@Resource
	private UserRegDTOMapper userMapper;
	@Resource
	private MemberRegDTOMapper memberMapper;
	@Resource
	private AreaRegDTOMapper areaMapper;
	@Resource
	private FamilyRegDTOMapper familyMapper;
	@Resource
	private JavaMailSender mailSender;
	@Resource
    private VelocityEngine velocityEngine;
	public List<UploadInfo> findUploadInfo(RoundCalander cal){
		
		return null;
		
	}

	/**
	 * 查询用户和关联角色信息
	 * @param name
	 * @param password
	 * @return
	 */
	public UserInfo findUser(String name, String password) {
		UserRegDTOExample example = new UserRegDTOExample();
		example.createCriteria().andEmailEqualTo(name)
		.andPasswordEqualTo(password);
		List<UserRegDTO> users = userMapper.selectByExample(example);
		if(users.size()==0){
			return null;
		}
		UserRegDTO userRegDTO = users.get(0);
		UserInfo info = new UserInfo();
		info.setEmail(userRegDTO.getEmail());
		info.setUserId(userRegDTO.getId());
		info.setCreateTime(userRegDTO.getCreateTime());
		
		Integer memId = userRegDTO.getMemberId();
		if(memId!=null){
			MemberRegDTO memberRegDTO = memberMapper.selectByPrimaryKey(memId);
			info.setGangId(memberRegDTO.getGangId());
			info.setLevel(memberRegDTO.getLevel());
			info.setFamilyId(memberRegDTO.getFamilyId());
			info.setName(memberRegDTO.getName());
			info.setRole(memberRegDTO.getRole());
			info.setMemberId(memId);
		}else{
			AreaRegDTO areaRegDTO = areaMapper.selectByPrimaryKey(users.get(0).getAreaId());
			info.setAreaId(areaRegDTO.getId());
		}
		return info;
	}

	public boolean isExistUser(String name) {
		UserRegDTOExample example = new UserRegDTOExample();
		example.createCriteria().andEmailEqualTo(name);
		
		return userMapper.selectByExample(example).size()!=0;
	}

	@Transactional
	public void insertUser(Map<String, String> args) {
		UserRegDTO record = new UserRegDTO();
		record.setCreateTime(new Date());
		record.setEmail(args.get("email"));
		record.setPassword(args.get("pwd"));
		record.setAreaId(args.get("areaId")!=null?Integer.parseInt(args.get("areaId")):null);
		record.setMemberId(args.get("memberId")!=null?Integer.parseInt(args.get("areaId")):null);
		userMapper.insert(record);
		sendConfirmationEmail(record);
	}
	 private void sendConfirmationEmail(final UserRegDTO user) {
	        MimeMessagePreparator preparator = new MimeMessagePreparator() {
	            public void prepare(MimeMessage mimeMessage) throws Exception {
	                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	                message.setTo(user.getEmail());
	                message.setSubject("欢迎加入别人家倩女");
	                message.setFrom(Const.MAIL_FROM_ADDRESS);
	                Map<String,Object> model = new HashMap<String,Object>();
	                model.put("user", user);
	                String text = VelocityEngineUtils.mergeTemplateIntoString(
	                        velocityEngine, "mail/welcome.vm","utf-8", model);
	                message.setText(text, true);
	            }
	        };
	        this.mailSender.send(preparator);
	    }


	public boolean isExistArea(String name) {
		AreaRegDTOExample example = new AreaRegDTOExample();
		example.createCriteria().andNameEqualTo(name);
		return areaMapper.selectByExample(example).size()!=0;
	}

	@Transactional
	public AreaRegDTO createArea(String name) {
		
		if(isExistArea(name)){
			return null;
		}
		AreaRegDTO record = new AreaRegDTO();
		record.setName(name);
		record.setCreateTime(new Date());
		areaMapper.insert(record);
		return record;
	}

	public List<MemberRegDTO> selectMemberList() {
		// TODO Auto-generated method stub
		return memberMapper.selectByExample(new MemberRegDTOExample());
	}

	public List<AreaRegDTO> selectAreaList() {
		// TODO Auto-generated method stub
		return areaMapper.selectByExample(new AreaRegDTOExample());
	}

	public FamilyRegDTO createFamily(String name,Integer userId, Integer memberId) {
		FamilyRegDTOExample example = new FamilyRegDTOExample();
		example.createCriteria().andNameEqualTo(name);
		List<FamilyRegDTO> list = familyMapper.selectByExample(example);
		if(!list.isEmpty()){
			return null;
		}
		FamilyRegDTO record = new FamilyRegDTO();
		record.setCreateTime(new Date());
		record.setCreateUser(userId);
		record.setName(name);
		familyMapper.insert(record);
		
		//memberId
		MemberRegDTO member = new MemberRegDTO();
		member.setId(memberId);
		member.setFamilyId(record.getId());
		memberMapper.updateByPrimaryKeySelective(member);
		return record;
	}

	public boolean updateRole(Integer roleId, UserInfo user) {
		MemberRegDTO memberRegDTO = memberMapper.selectByPrimaryKey(roleId);
		if(memberRegDTO==null){
			return false;
		}
		UserRegDTO record = new UserRegDTO(); 
		record.setId(user.getUserId());
		record.setMemberId(roleId);
		record.setAreaId(record.getAreaId());
		if(userMapper.updateByPrimaryKeySelective(record)>0){
			user.setAreaId(memberRegDTO.getAreaId());
			user.setGangId(memberRegDTO.getGangId());
			user.setMemberId(memberRegDTO.getId());
			user.setName(memberRegDTO.getName());
			return true;
		}
		return false;
		
	}
}
