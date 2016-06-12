package org.brjia.qs.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.brjia.qs.model.AreaRegDTO;
import org.brjia.qs.model.FamilyRegDTO;
import org.brjia.qs.round.MemberListData;
import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.service.FightingService;
import org.brjia.qs.service.UserService;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.Family;
import org.brjia.qs.util.FamilyMetaData;
import org.brjia.qs.util.ResultMessage;
import org.brjia.qs.util.SummaryMetaData;
import org.brjia.qs.util.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 家族数据控制器
 * @author hujiawei
 *
 */
@Controller
@RequestMapping("family")
public class FamilyController {

	@Resource
	private FightingService fightingService;
	@Resource
	private UserService userService;
	/**
	 * 家族成员多维度数据
	 */
	@RequestMapping("listdata")
	@ResponseBody
	public ResultMessage memberData(@RequestParam Integer fid){
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.findFamilyListData(new RoundCalander(), fid));
		return rm;
	}
	/**
	 * 全部家族
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "list",method = RequestMethod.GET)
	public String areaIndex(HttpSession ses,ModelMap modelMap) throws IOException {
		List<Family> familyList = fightingService.findFamilyList();
		if(familyList==null){
			modelMap.addAttribute("msg_code", Const.MSG_NO_ANY_FAMILY);
			return "msg";
		}
		UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("familys", familyList);
		return "familylist";
	}
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage createArea(@RequestParam("familyName") String name,HttpSession ses) {
		ResultMessage ms = new ResultMessage();
		UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		if(user==null){
			ms.setMsg("未登录");
			return ms;
		}
		if(user.getMemberId()==null){
			ms.setMsg("尚未关联游戏角色");
			return ms;
		}
		if(user.getFamilyId()!=null){
			ms.setMsg("需先退出家族后，才允许继续操作");
			return ms;
		}
		FamilyRegDTO res = userService.createFamily(name,user.getUserId(),user.getMemberId());
		if(res==null){
			ms.setMsg("该名称已存在");
			return ms;
		}
		user.setFamilyId(res.getId());
		ms.setData(res);
		return ms;
	}
	 /**
	  * 家族主页
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "",method = RequestMethod.GET)
	 public String index(HttpSession ses,ModelMap modelMap) throws IOException {
		 UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		 if(user==null){
			 return "forward:/family/list";
		 }
		 if(user.getGangId()==null){
			 modelMap.addAttribute("msg_code", Const.MSG_NO_MY_FAMILY);
			 return "msg";
		 }
		return "redirect:"+user.getFamilyId();
	 }
	 /**
	 * 跳转到目标家族
	 * @return 
	 */
	@RequestMapping(value="/{fid:\\d*}",method=RequestMethod.GET)
	public String outlineData(@PathVariable("fid") Integer familyId,HttpSession ses,ModelMap modelMap) {
		FamilyMetaData family = fightingService.findFamily(familyId);
		if(family==null){
			modelMap.addAttribute("msg_code", Const.MSG_NO_FAMILY_DATA);
			return "msg";
		}
		modelMap.addAttribute("user", ses.getAttribute(Const.USER_KEY));
		modelMap.addAttribute("fmeta", family);
		return "family";
		
	}
}
