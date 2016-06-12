package org.brjia.qs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.brjia.qs.mapper.FightingListDataRegDTOMapper;
import org.brjia.qs.mapper.GangRegDTOMapper;
import org.brjia.qs.mapper.MemberRegDTOMapper;
import org.brjia.qs.mapper.SummaryDataRegDTOMapper;
import org.brjia.qs.mapper.UploadStateRegDTOMapper;
import org.brjia.qs.model.FightingListDataRegDTO;
import org.brjia.qs.model.GangRegDTO;
import org.brjia.qs.model.GangRegDTOExample;
import org.brjia.qs.model.MemberRegDTO;
import org.brjia.qs.model.MemberRegDTOExample;
import org.brjia.qs.model.SummaryDataRegDTO;
import org.brjia.qs.model.UploadStateRegDTO;
import org.brjia.qs.model.UploadStateRegDTOExample;
import org.brjia.qs.parse.beans.Auxiliary;
import org.brjia.qs.parse.beans.Comprehensive;
import org.brjia.qs.parse.beans.Outline;
import org.brjia.qs.parse.beans.OutputAmount;
import org.brjia.qs.parse.beans.Tank;
import org.brjia.qs.parse.parser.TextParser;
import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.round.UploadRoundCalander;
import org.brjia.qs.util.QnyhCollectArg;
import org.brjia.qs.util.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * 2015年10月9日
 */
@Service("collectService")
public class CollectService {

	private static Logger _log = Logger.getLogger(CollectService.class);
	@Resource
	private TextParser textParser;
	@Resource
	private FightingListDataRegDTOMapper activityDao;
	@Resource
	private SummaryDataRegDTOMapper gaRelDao;
	@Resource
	private GangRegDTOMapper gangDao;
	@Resource
	private MemberRegDTOMapper memberDao;
	
	@Resource
	private UploadStateRegDTOMapper uploadMapper;
	/**
	 * 
	 * @param info
	 * @param args
	 * @return
	 */
	@Transactional
	public String insertFightingSummary(UserInfo info,List<QnyhCollectArg> args){
		Outline outline=null;
		RoundCalander fightingCalander = null;
		for(QnyhCollectArg arg:args){
			try {
	        	outline = textParser.parse(arg.getContent());
				arg.setOutline(outline);
				fightingCalander = new UploadRoundCalander(textParser.parseDate(arg.getFilename()));
				arg.setFightTime(fightingCalander);
				
				if(exist(fightingCalander,outline.getGang(),arg.getMdStr())){
					return "重复上传";
				}
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				_log.error("非法文件名，时间范围异常："+arg.getFilename());
				return "非法文件名";
			}catch (Exception e) {
				e.printStackTrace();
				_log.error("文件解析异常："+arg.getFilename());
				return "非法文件";
			}
		}
		Integer sumId;
		for(QnyhCollectArg arg:args){
			sumId= summaryAndPersist(arg.getFightTime(),arg.getOutline(),info.getAreaId());
			insertUploadState(arg.getFightTime(),arg.getOutline().getGang(),info,arg.getMdStr(),sumId);
		}
		return null;
	}
	private void insertUploadState(RoundCalander fightingCalander, String gang, UserInfo info, String mdStr, Integer sumId) {
		UploadStateRegDTO record = new UploadStateRegDTO();
		record.setAreaid(info.getAreaId());
		record.setCreateTime(new Date());
		record.setCreateUser(info.getUserId());
		record.setGangName(gang);
		record.setSignature(mdStr);
		record.setSummaryId(sumId);
		record.setYear(fightingCalander.getYear());
		record.setWeek(fightingCalander.getWeek());
		record.setRound(fightingCalander.getRound());
		uploadMapper.insert(record);
		
	}
	private boolean exist(RoundCalander cal, String gangName, String signature) {
		UploadStateRegDTOExample example = new UploadStateRegDTOExample();
		example.createCriteria()
		.andYearEqualTo(cal.getYear())
		.andWeekEqualTo(cal.getWeek())
		.andRoundEqualTo(cal.getRound())
		.andGangNameEqualTo(gangName)
		.andSignatureEqualTo(signature);
		List<UploadStateRegDTO> selectByExample = uploadMapper.selectByExample(example);
		return selectByExample.size()!=0;
	}
	/**
	 * @param calendar
	 * @param outline
	 * @param areaId 
	 * @return 
	 */
	private Integer summaryAndPersist(RoundCalander calendar, Outline outline, Integer areaId) {

		GangRegDTOExample example = new GangRegDTOExample();
		example.createCriteria().andAreaIdEqualTo(areaId);
		List<GangRegDTO> gangs = gangDao.selectByExample(example);
		
		String curGangName = outline.getGang();
		Integer curGangID = getGangID(curGangName, gangs);
		if(curGangID==null){
			//插入新帮会
			GangRegDTO record = new GangRegDTO();
			record.setName(curGangName);
			record.setAreaId(areaId);
			record.setCreateTime(new Date());
			gangDao.insert(record);
			curGangID = record.getId();
		}
		
		//日历工具类辅助计算
		//查询区域下所有会员
		//是否新会员
		//插入新会员
		//活动数据-人员ID，是否英雄，是否开车
		
		List<FightingListDataRegDTO> acts = generateActivitys(outline,areaId,curGangID,calendar);
		
		//战斗数据汇总
		SummaryDataRegDTO statistic = generateGangActivitys(acts);
		//非战斗元数据统计-id,参战人数，不挂机人数，持续时间，创建时间
		statistic.setYear(calendar.getYear());
		statistic.setWeek(calendar.getWeek());
		statistic.setRound(calendar.getRound());
		statistic.setGangId(curGangID);
		statistic.setGangName(curGangName);
		statistic.setAreaId(areaId);
		statistic.setDuration(calendar.getOffsetMinute());
		statistic.setCount(acts.size());
//		statistic.setDeadCount();已经计算得出
		statistic.setUploadDate(calendar.getDate());
		gaRelDao.insert(statistic);
		
		for(FightingListDataRegDTO act:acts){
			act.setSummaryId(statistic.getId());
			activityDao.insert(act);
		}
		return statistic.getId();
	}
	
	/**
	 * @param acts
	 * @param curGangID 
	 * @param ywrID 
	 * @return
	 */
	private SummaryDataRegDTO generateGangActivitys(List<FightingListDataRegDTO> acts) {
		SummaryDataRegDTO res = new SummaryDataRegDTO();
		int[] data = new int[13];
		FightingListDataRegDTO act=null;
		for(int i=0,ln=acts.size();i<ln;i++){
			act=acts.get(i);
			data[0]+=act.getKillQ();
			data[1]+=act.getAssistQ();
			data[2]+=act.getCarryQ();
			data[3]+=act.getOutputM();
			data[4]+=act.getCureM();
			data[5]+=act.getBearM();
			data[6]+=act.getCorpseQ();
			data[7]+=act.getReliveQ();
			data[8]+=act.getKilledQ();
			data[9]+=act.getTankKillQ();
			data[10]+=act.getHero().equals("1")?1:0;
			data[11]+=act.getDriver().equals("1")?1:0;
			
			
			if(act.getKillQ()<1 && act.getCarryQ()<2 && act.getAssistQ()<5 && act.getTankKillQ()<1
					&& act.getCureM()<10000 && act.getCorpseQ()<5 && act.getBearM()<100000  
					&& act.getOutputM()<100000 && act.getReliveQ()<2 && act.getKilledQ()<3){
				data[12]++;
			}
			
		}
		res.setKillQ(data[0]);
		res.setAssistQ(data[1]);
		res.setCarryQ(data[2]);
		res.setOutputM(data[3]);
		res.setCureM(data[4]);
		res.setBearM(data[5]);
		res.setCorpseQ(data[6]);
		res.setReliveQ(data[7]);
		res.setKilledQ(data[8]);
		res.setTankKillQ(data[9]);
		res.setHero(data[10]);
		res.setDriver(data[11]);
		res.setDeadCount(data[12]);
		return res;
	}
	
	/**
	 * @param outline
	 * @param areaId 
	 * @param curGangID 
	 * @param ywrID 
	 * @return
	 */
	private List<FightingListDataRegDTO> generateActivitys(Outline outline, Integer areaId, Integer curGangID, RoundCalander calendar) {
		MemberRegDTOExample example = new MemberRegDTOExample();
		example.createCriteria().andAreaIdEqualTo(areaId);
		List<MemberRegDTO> members = memberDao.selectByExample(example);
		List<Comprehensive> main = outline.getMain();
		List<Auxiliary> assist = outline.getAssist();
		List<Tank> tanks = outline.getTank();
		List<OutputAmount> out = outline.getOut();
		List<OutputAmount> cure = outline.getCure();
		List<OutputAmount> bear = outline.getBear();

		List<FightingListDataRegDTO> acts = new ArrayList<FightingListDataRegDTO>();
		FightingListDataRegDTO act = null;
		MemberRegDTO reg = null;
		for(int i=0;i<main.size();i++){
			Comprehensive comprehensive = main.get(i);
			Auxiliary auxiliary = assist.get(i);
			Tank tank = tanks.get(i);
			
			String memberName = comprehensive.getName();
			
			Integer memberID = getMemberID(memberName,members);
			if(memberID==null){
				reg = new MemberRegDTO();
				reg.setGangId(curGangID);
				reg.setLevel(main.get(i).getLevel());
				reg.setName(memberName);
				reg.setRole(comprehensive.getProfession());
				reg.setAreaId(areaId);
				reg.setCreateTime(new Date());
				memberDao.insert(reg);
				memberID = reg.getId();
			}
			
			act = new FightingListDataRegDTO();
			act.setMemberId(memberID);
			act.setMemberName(memberName);
			act.setLevel(comprehensive.getLevel());
			
			act.setKillQ(comprehensive.getKill());
			act.setAssistQ(comprehensive.getAssists());
			act.setCarryQ(comprehensive.getRepair());
			act.setHero(comprehensive.getAccumulate()==0?"0":"1");
			
			act.setKilledQ(auxiliary.getKilledCount());
			act.setCorpseQ(auxiliary.getBaoshi());
			act.setReliveQ(auxiliary.getRelive());
			
			act.setTankKillQ(tank.getKill());
			act.setDriver(tank.getDrive()==0?"0":"1");
			
			act.setBearM(getFromList(comprehensive.getName(),bear));
			act.setOutputM(getFromList(comprehensive.getName(),out));
			act.setCureM(getFromList(comprehensive.getName(),cure));
			
			act.setYear(calendar.getYear());
			act.setAreaId(areaId);
//			act.setSummaryId(summaryId);后面设置
			acts.add(act);
		}
		return acts;
	}
	/**
	 * @param name
	 * @param bear
	 * @return
	 */
	private Integer getFromList(String name, List<OutputAmount> list) {
		for(OutputAmount a:list){
			if(a.getName().equals(name)){
				
				return a.getAmount()/10000;
			}
		}
		return null;
	}
	
	/**
	 * @param memberName
	 * @param members
	 * @return
	 */
	private Integer getMemberID(String memberName, List<MemberRegDTO> members) {
		for(MemberRegDTO reg:members){
			if(reg.getName().equals(memberName)){
				return reg.getId();
			}
		}
		return null;
	}
	/**
	 * @param curGangID
	 * @param gangs
	 * @return 
	 */
	private Integer getGangID(String curGangName, List<GangRegDTO> gangs) {
		for(GangRegDTO reg:gangs){
			
			if(reg.getName().equals(curGangName)){
				return reg.getId();
			}
		}
		return null;
	}
}

