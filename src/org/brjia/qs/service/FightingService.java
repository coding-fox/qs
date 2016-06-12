package org.brjia.qs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.brjia.qs.mapper.AreaRegDTOMapper;
import org.brjia.qs.mapper.FamilyRegDTOMapper;
import org.brjia.qs.mapper.FightingListDataRegDTOMapper;
import org.brjia.qs.mapper.GangRegDTOMapper;
import org.brjia.qs.mapper.MemberRegDTOMapper;
import org.brjia.qs.mapper.SummaryDataRegDTOMapper;
import org.brjia.qs.mapper.TestMapper;
import org.brjia.qs.mapper.UploadStateRegDTOMapper;
import org.brjia.qs.model.AreaRegDTO;
import org.brjia.qs.model.AreaRegDTOExample;
import org.brjia.qs.model.FamilyRegDTO;
import org.brjia.qs.model.FamilyRegDTOExample;
import org.brjia.qs.model.FightingListDataRegDTO;
import org.brjia.qs.model.FightingListDataRegDTOExample;
import org.brjia.qs.model.GangRegDTO;
import org.brjia.qs.model.GangRegDTOExample;
import org.brjia.qs.model.MemberRegDTO;
import org.brjia.qs.model.MemberRegDTOExample;
import org.brjia.qs.model.SummaryDataRegDTO;
import org.brjia.qs.model.SummaryDataRegDTOExample;
import org.brjia.qs.model.SummaryDataRegDTOExample.Criteria;
import org.brjia.qs.model.UploadStateRegDTO;
import org.brjia.qs.model.UploadStateRegDTOExample;
import org.brjia.qs.round.HeroData;
import org.brjia.qs.round.MemberListData;
import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.round.RoundHeroData;
import org.brjia.qs.round.RoundListData;
import org.brjia.qs.round.RoundSummaryData;
import org.brjia.qs.round.SummaryData;
import org.brjia.qs.round.UploadInfo;
import org.brjia.qs.util.AreaGang;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.Family;
import org.brjia.qs.util.FamilyData;
import org.brjia.qs.util.FamilyMetaData;
import org.brjia.qs.util.Gang;
import org.brjia.qs.util.SummaryMetaData;
import org.brjia.qs.util.UploadMetaData;
import org.springframework.stereotype.Service;

@Service
public class FightingService {

	private TestMapper testMapper;

	private static Logger _log = Logger.getLogger(FightingService.class);
	@Resource
	private FightingListDataRegDTOMapper fightingMapper;
	@Resource
	private SummaryDataRegDTOMapper summaryMapper;
	@Resource
	private GangRegDTOMapper gangMapper;
	@Resource
	private MemberRegDTOMapper memberMapper;
	@Resource
	private FamilyRegDTOMapper familyMapper;
	@Resource
	private UploadStateRegDTOMapper uploadMapper;
	@Resource
	private AreaRegDTOMapper areaMapper;

	public String queryName(Integer id) {
		return testMapper.selectName(id);
	}

	public TestMapper getTestMapper() {
		return testMapper;
	}

	public void setTestMapper(TestMapper testMapper) {
		this.testMapper = testMapper;
	}

	/**
	 * 按照指定期次和帮会进行查询
	 * 
	 * @param cal
	 * @param selfGangId
	 *            自己帮会id
	 * @param areaId
	 *            在搜索敌对帮会时，可以缩小搜索范围，提高准确性，避免重名//TODO
	 * @return
	 */
	public RoundSummaryData findSummaryData(RoundCalander cal, Integer selfGangId, Integer areaId) {
		// query my gang
		SummaryDataRegDTO summaryData = querySummaryData(cal, selfGangId);
		if (summaryData == null) {
			return null;
		}
		RoundSummaryData res = new RoundSummaryData();
		res.setPrimary(translate(summaryData));
		res.setEnemy(translate(getEnemySummary(cal, summaryData)));
		return res;

	}

	private SummaryDataRegDTO getEnemySummary(RoundCalander cal, SummaryDataRegDTO summaryData) {
		Integer enemyID = findEnemyId(cal, summaryData);

		return querySummaryData(enemyID);
	}

	private Integer findEnemyId(RoundCalander cal, SummaryDataRegDTO summaryData) {
		Integer enemyID = getEnemySummaryId(cal, summaryData);
		if (enemyID == null) {
			return null;
		}

		insertSummaryId(summaryData.getId(), enemyID);
		return enemyID;
	}

	private Integer getEnemySummaryId(RoundCalander cal, SummaryDataRegDTO summaryData) {
		Integer enemyID = summaryData.getEnemySummaryId();
		if (enemyID == null) {
			enemyID = queryEnemyId(cal, summaryData.getAreaId(), summaryData.getGangId(), summaryData.getKillQ(),
					summaryData.getKilledQ(), summaryData.getTankKillQ());
		}
		return enemyID;
	}

	/**
	 * 按照帮会查询有数据的最近期次
	 * 
	 * @param gangId
	 * @return
	 */
	public SummaryMetaData findLastSummaryID(Integer selfGangId) {
		SummaryDataRegDTO summaryData = queryLastSummaryDataByGang(selfGangId);
		if (summaryData == null) {
			return null;
		}
		SummaryMetaData res = new SummaryMetaData();
		RoundCalander cal = new RoundCalander(summaryData.getUploadDate());
		res.setSid(summaryData.getId());
		res.setEid(findEnemyId(cal, summaryData));
		res.setYear(cal.getYear());
		res.setWeek(cal.getWeek());
		res.setRound(cal.getRound());
		res.setDateb(cal.getDataBandStr());
		return res;

	}

	/**
	 * 按照帮会查询有数据的最近期次
	 * 
	 * @param gangId
	 * @return
	 */
	public RoundSummaryData findLastSummaryData(Integer selfGangId) {

		SummaryDataRegDTO summaryData = queryLastSummaryDataByGang(selfGangId);
		if (summaryData == null) {
			return null;
		}
		// 这里也可以用UploadRoundCalander类构造round
		RoundCalander cal = new RoundCalander(summaryData.getUploadDate());
		RoundSummaryData res = new RoundSummaryData();
		res.setPrimary(translate(summaryData));
		res.setEnemy(translate(getEnemySummary(cal, summaryData)));
		return res;

	}

	private SummaryData translate(SummaryDataRegDTO sour) {
		if (sour == null) {
			return null;
		}
		SummaryData res = new SummaryData();
		res.setBearM(sour.getBearM());
		res.setGangName(sour.getGangName());
		res.setAssistQ(sour.getAssistQ());
		res.setOutputM(sour.getOutputM());
		res.setReliveQ(sour.getReliveQ());
		res.setKillQ(sour.getKillQ());
		res.setCureM(sour.getCureM());
		res.setCorpseQ(sour.getCorpseQ());
		res.setHero(sour.getHero());
		res.setDriver(sour.getDriver());
		res.setCount(sour.getCount());
		res.setDeadCount(sour.getDeadCount());
		res.setGangId(sour.getGangId());
		res.setTankKillQ(sour.getTankKillQ());
		res.setCarryQ(sour.getCarryQ());
		res.setId(sour.getId());
		return res;
	}

	/**
	 * 获取英雄汇总数据
	 * 
	 * @param sumaryId
	 * @param enemySummaryId
	 * @return
	 */
	public RoundHeroData findHeroData(Integer sumaryId, Integer enemySummaryId) {
		if (sumaryId == null) {
			return null;
		}
		// query 5 record
		FightingListDataRegDTOExample example = new FightingListDataRegDTOExample();
		if (enemySummaryId == null) {
			example.createCriteria().andHeroEqualTo(Const.TRUE_STRING).andSummaryIdEqualTo(sumaryId);
		} else {
			example.createCriteria().andHeroEqualTo(Const.TRUE_STRING)
					.andSummaryIdIn(Arrays.asList(sumaryId, enemySummaryId));
		}
		List<FightingListDataRegDTO> heroList = fightingMapper.selectByExample(example);
		if (heroList.size() == 0) {
			return new RoundHeroData();
		}

		// 汇总
		RoundHeroData res = new RoundHeroData();
		res.setPrimary(summaryToHero(heroList, sumaryId));
		res.setEnemy(summaryToHero(heroList, enemySummaryId));
		return res;

	}

	private HeroData summaryToHero(List<FightingListDataRegDTO> heroList, Integer sumaryId) {
		if (sumaryId == null) {
			return null;
		}
		int killQ = 0, assistQ = 0, outputM = 0, cureM = 0, bearM = 0, reliveQ = 0;
		List<String> heroName = new ArrayList<>();
		for (FightingListDataRegDTO item : heroList) {
			if (item.getSummaryId().intValue() == sumaryId) {
				killQ += item.getKillQ();
				assistQ += item.getAssistQ();
				outputM += item.getOutputM();
				cureM += item.getCureM();
				bearM += item.getBearM();
				reliveQ += item.getReliveQ();
				heroName.add(item.getMemberName());
			}
		}
		HeroData res = new HeroData();
		res.setKillQ(killQ);
		res.setAssistQ(assistQ);
		res.setOutputM(outputM);
		res.setCureM(cureM);
		res.setBearM(bearM);
		res.setHero(heroName);
		res.setReliveQ(reliveQ);
		return res;
	}

	public RoundListData findMemeberListData(Integer summaryId, Integer enemySummaryId) {

		RoundListData res = new RoundListData();

		FightingListDataRegDTOExample example = new FightingListDataRegDTOExample();
		example.createCriteria().andSummaryIdEqualTo(summaryId);
		List<FightingListDataRegDTO> resList = fightingMapper.selectByExample(example);
		res.setPrimary(translateMemberList(resList));

		if (enemySummaryId == null) {
			return res;
		}
		example = new FightingListDataRegDTOExample();
		example.createCriteria().andSummaryIdEqualTo(enemySummaryId);
		resList = fightingMapper.selectByExample(example);
		res.setEnemy(translateMemberList(resList));

		return res;

	}

	private List<MemberListData> translateMemberList(List<FightingListDataRegDTO> sourList) {
		if (sourList.size() == 0) {
			return null;
		}
		List<MemberListData> resList = new ArrayList<MemberListData>();
		MemberListData res = null;
		for (FightingListDataRegDTO sour : sourList) {
			res = new MemberListData();
			res.setAssistQ(sour.getAssistQ());
			res.setOutputM(sour.getOutputM());
			res.setBearM(sour.getBearM());
			res.setKillQ(sour.getKillQ());
			res.setCureM(sour.getCureM());
			res.setCorpseQ(sour.getCorpseQ());
			res.setHero(sour.getHero());
			res.setKilledQ(sour.getKilledQ());
			res.setCarryQ(sour.getCarryQ());
			res.setReliveQ(sour.getReliveQ());
			res.setMemberId(sour.getMemberId());
			res.setMemberName(sour.getMemberName());
			res.setSummaryId(sour.getSummaryId());
			res.setLevel(sour.getLevel());
			resList.add(res);
		}
		return resList;
	}

	/**
	 * 
	 * @param cal
	 * @param familyId
	 * @return
	 */
	public FamilyData findFamilyListData(RoundCalander cal, Integer familyId) {
		if (familyId == null) {
			return null;
		}
		// 查询家族所有会员
		MemberRegDTOExample memExa = new MemberRegDTOExample();
		memExa.createCriteria().andFamilyIdEqualTo(familyId);
		List<MemberRegDTO> memRegs = memberMapper.selectByExample(memExa);
		List<Integer> memIds = memRegs.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (memIds.isEmpty()) {
			return null;
		}
		// 查询当期所有summaryid
		SummaryDataRegDTOExample sumExa = new SummaryDataRegDTOExample();
		sumExa.createCriteria().andYearEqualTo(cal.getYear()).andWeekEqualTo(cal.getWeek())
				.andRoundEqualTo(cal.getRound());
		List<SummaryDataRegDTO> summarys = summaryMapper.selectByExample(sumExa);
		if (summarys.isEmpty()) {
			return null;
		}
		List<Integer> sumIds = summarys.stream().map(item -> item.getId()).collect(Collectors.toList());
		// 查询当期家族成员数据
		FightingListDataRegDTOExample example = new FightingListDataRegDTOExample();
		example.createCriteria().andSummaryIdIn(sumIds).andMemberIdIn(memIds);
		List<FightingListDataRegDTO> list = fightingMapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		FamilyData res = new FamilyData();
		res.setPrimary(translateMemberList(list));
		return res;

	}

	private void insertSummaryId(Integer id, Integer enemy) {
		SummaryDataRegDTO record = new SummaryDataRegDTO();
		record.setId(id);
		record.setEnemySummaryId(enemy);
		summaryMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据id查询汇总
	 * 
	 * @param summaryID
	 * @return
	 */
	private SummaryDataRegDTO querySummaryData(Integer summaryID) {

		return summaryMapper.selectByPrimaryKey(summaryID);
	}

	/**
	 * 搜索指定期次指定区下，搜索敌对帮会汇总id 使用杀敌人数和被杀人数进行匹配 <br/>
	 * 敌killedQ=我killQ+我tankKillQ/2+我箭塔杀敌数<br/>
	 * 由于箭塔数量未知，无法精确匹配。 仍然可以使用范围进行匹配，如果箭塔杀人系数定义为每杀敌100，其中箭塔最多杀几人。目测值2%：<br/>
	 * 我箭塔杀人数＝我killQ＊1% <br/>
	 * 最终匹配公式为： <br/>
	 * 敌killedQ<=我killQ*1.01+我tankKillQ/2 $$ 敌killedQ>=我killQ+我tankKillQ/2
	 * 
	 * 搜索逻辑，首先0%精确查找 如果找到，匹配成功<br/>
	 * 如果未找到，以一个默认箭塔杀敌系数进行范围搜索；<br/>
	 * 
	 * 如果多余2条满足，则减少箭塔杀敌系数，每次减少1，直至仅有一条纪录匹配，否则匹配失败； <br/>
	 * 如果少于1条满足，则增加箭塔杀敌系数，每次增加1，直至仅有一条纪录匹配，否则匹配失败；<br/>
	 * 
	 * @param cal
	 * @param areaId
	 *            此参数是必需的，搜索是针对同一个区的
	 * @param gangId
	 * @param kill
	 * @param killed
	 * @param tankQ
	 * @return
	 */
	private Integer queryEnemyId(RoundCalander cal, Integer areaId, Integer selfGangId, Integer kill, Integer killed,
			Integer tankQ) {
		List<SummaryDataRegDTO> resultList = accuracyMatch(cal, areaId, selfGangId, kill, killed, tankQ);
		if (resultList.size() > 0) {
			if (resultList.size() > 1) {
				_log.warn("执行精确匹配敌对帮会时，意外获取到多个匹配结果" + cal + selfGangId);
			}
			return resultList.get(0).getId();
		}
		// 精确匹配失败，执行范围匹配
		float tower = Const.TOWE_KILL_RATIO;
		resultList = rangeMatch(cal, areaId, selfGangId, kill, killed, tankQ, tower);

		if (resultList.size() == 0) {// 一次范围匹配数量为0
			tower = tower + 0.01f;
			resultList = rangeMatch(cal, areaId, selfGangId, kill, killed, tankQ, tower);
			if (resultList.size() == 0) {// 无法判断是系数原因还是根本没有匹配数据
				return null;
			}
			if (resultList.size() > 1) {
				_log.warn("增高箭塔杀敌系数后返回多个匹配结果" + cal + selfGangId + "size:" + resultList.size());
				return null;
			}
			if (_log.isInfoEnabled()) {
				_log.info("增高箭塔杀敌系数后匹配成功" + cal + selfGangId);
			}
			return resultList.get(0).getId();// 二次匹配成功
		}
		if (resultList.size() > 1) {// 一次范围匹配数量过多
			_log.warn("第一次范围匹配，返回多个匹配结果" + cal + selfGangId + "size:" + resultList.size());
			tower = tower - 0.01f;

			resultList = rangeMatch(cal, areaId, selfGangId, kill, killed, tankQ, tower);
			if (resultList.size() == 0) {
				_log.warn("降低箭塔杀敌系数后无匹配结果" + cal + selfGangId);
				return null;
			}
			if (resultList.size() > 1) {
				_log.warn("降低箭塔杀敌系数后依然返回多个匹配结果" + cal + selfGangId + "size:" + resultList.size());
				return null;
			}
			if (_log.isInfoEnabled()) {
				_log.info("降低箭塔杀敌系数后匹配成功" + cal + selfGangId);
			}
			return resultList.get(0).getId();// 二次匹配成功
		}

		return resultList.get(0).getId();// 一次范围匹配成功
	}

	// TODO 数据结构变动风险
	private List<SummaryDataRegDTO> rangeMatch(RoundCalander cal, Integer areaId, Integer selfGangId, Integer kill,
			Integer killed, Integer tankQ, float tower) {
		List<SummaryDataRegDTO> resultList;
		SummaryDataRegDTOExample example = new SummaryDataRegDTOExample();
		Criteria criteria = example.createCriteria();
		criteria.andYearEqualTo(cal.getYear()).andWeekEqualTo(cal.getWeek()).andRoundEqualTo(cal.getRound())
				.andAreaIdEqualTo(areaId).andGangIdNotEqualTo(selfGangId)
				.andKilledQBetween(kill + tankQ / 2, Math.round(kill * (1 + tower)) + tankQ / 2)
				.addCriterion("kill_q >= - tank_kill_q / 2+", Math.round(killed * (1 - tower)), null);
		criteria.addCriterion("kill_q <= - tank_kill_q / 2+", killed, null);
		resultList = summaryMapper.selectByExample(example);
		return resultList;
	}

	// TODO 数据结构变动风险
	private List<SummaryDataRegDTO> accuracyMatch(RoundCalander cal, Integer areaId, Integer selfGangId, Integer kill,
			Integer killed, Integer tankQ) {
		SummaryDataRegDTOExample example = new SummaryDataRegDTOExample();
		example.createCriteria().andYearEqualTo(cal.getYear()).andWeekEqualTo(cal.getWeek())
				.andRoundEqualTo(cal.getRound()).andAreaIdEqualTo(areaId).andGangIdNotEqualTo(selfGangId)
				.andKilledQEqualTo(kill + tankQ / 2).addCriterion("kill_q = - tank_kill_q / 2+", killed, null);
		List<SummaryDataRegDTO> resultList = summaryMapper.selectByExample(example);
		return resultList;
	}

	/**
	 * 根据帮会id、指定期次的查询汇总数据
	 * 
	 * @param cal
	 * @param gangId
	 * @return
	 */
	private SummaryDataRegDTO querySummaryData(RoundCalander cal, Integer gangId) {
		SummaryDataRegDTOExample example = new SummaryDataRegDTOExample();
		example.createCriteria().andYearEqualTo(cal.getYear()).andWeekEqualTo(cal.getWeek())
				.andRoundEqualTo(cal.getRound()).andGangIdEqualTo(gangId);
		List<SummaryDataRegDTO> resultList = summaryMapper.selectByExample(example);
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	/**
	 * 根据帮会id查询最近一场汇总数据
	 * 
	 * @param gangId
	 * @return
	 */
	private SummaryDataRegDTO queryLastSummaryDataByGang(Integer gangId) {
		SummaryDataRegDTOExample example = new SummaryDataRegDTOExample();
		example.createCriteria().andGangIdEqualTo(gangId);
		example.setOrderByClause("upload_date desc limit 1");// TODO 数据结构变动风险
		List<SummaryDataRegDTO> resultList = summaryMapper.selectByExample(example);
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	/**
	 * 查询帮会前1月的所有上传纪录
	 * 
	 * @param gangId
	 * @return
	 */
	public List<UploadMetaData> findUploadInfo(Integer gangId) {
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.MONTH, -1);
		SummaryDataRegDTOExample gangExa = new SummaryDataRegDTOExample();
		gangExa.createCriteria().andGangIdEqualTo(gangId).andUploadDateGreaterThan(instance.getTime());
		gangExa.setOrderByClause("upload_date desc");

		List<SummaryDataRegDTO> sumarys = summaryMapper.selectByExample(gangExa);
		if (sumarys.isEmpty()) {
			return null;
		}

		List<Integer> ids = sumarys.stream().map(item -> item.getId()).collect(Collectors.toList());
		UploadStateRegDTOExample uploadExa = new UploadStateRegDTOExample();
		uploadExa.createCriteria().andSummaryIdIn(ids);
		List<UploadStateRegDTO> uploads = uploadMapper.selectByExample(uploadExa);
		List<UploadMetaData> result = new ArrayList<>();
		sumarys.forEach(reg -> {
			UploadMetaData data = new UploadMetaData();
			data.setYear(reg.getYear());
			data.setWeek(reg.getWeek());
			data.setRound(reg.getRound());
			data.setSid(reg.getId());
			data.setEid(reg.getEnemySummaryId());
			UploadStateRegDTO upload = uploads.stream().filter(d -> d.getSummaryId().equals(reg.getId())).findFirst()
					.get();
			data.setImpetus(Math.round(calcImpetus(reg)));
			data.setUploadUserName(upload.getCreateUser() + "");// TODO find
																// user name
			data.setCreateTime(upload.getCreateTime());
			data.setFightTime(reg.getUploadDate());
			result.add(data);
		});
		return result;
	}

	/**
	 * 计算声势值
	 * 
	 * @param reg
	 * @return
	 */
	private int calcImpetus(SummaryDataRegDTO reg) {
		return reg.getOutputM() / 1000 + reg.getCureM() / 100 + reg.getAssistQ() / 8 + reg.getKillQ()
				+ reg.getTankKillQ()+reg.getCarryQ();
	}

	/**
	 * 查询当期的所有上传纪录
	 * 
	 * @return
	 */
	public List<UploadInfo> findUploadInfo() {
		RoundCalander cal = new RoundCalander();
		UploadStateRegDTOExample example = new UploadStateRegDTOExample();
		example.createCriteria().andYearEqualTo(cal.getYear()).andWeekEqualTo(cal.getWeek())
				.andRoundEqualTo(cal.getRound());

		List<UploadStateRegDTO> list = uploadMapper.selectByExample(example);
		return translateUpload(list);
	}

	private List<UploadInfo> translateUpload(List<UploadStateRegDTO> list) {
		if (list.size() == 0) {
			return null;
		}
		List<UploadInfo> result = new ArrayList<UploadInfo>();
		UploadInfo res = null;
		for (UploadStateRegDTO item : list) {
			res = new UploadInfo();
			res.setSummaryId(item.getSummaryId());
			res.setAreaId(item.getAreaid());
			res.setAreaName(item.getAreaid() + "");
			res.setGangName(item.getGangName());
			res.setUploadUserName(item.getCreateUser() + "");
			res.setUploadTime(item.getCreateTime());
			result.add(res);
		}
		return result;
	}

	public List<AreaGang> findGangList() {
		AreaRegDTOExample example = new AreaRegDTOExample();
		List<AreaRegDTO> areas = areaMapper.selectByExample(example);
		GangRegDTOExample gangCon = new GangRegDTOExample();
		List<GangRegDTO> gangs = gangMapper.selectByExample(gangCon);

		List<AreaGang> result = new ArrayList<AreaGang>();

		areas.forEach((item) -> {
			AreaGang resItem = new AreaGang();
			List<Gang> areaGangs = new ArrayList<Gang>();
			gangs.stream().filter(g -> g.getAreaId().equals(item.getId())).forEach(gr -> {
				Gang resg = new Gang();
				resg.setCreateTime(gr.getCreateTime());
				resg.setGangId(gr.getId());
				resg.setGangName(gr.getName());
				resg.setLevel(gr.getLevel());
				areaGangs.add(resg);
			});
			resItem.setAreaId(item.getId());
			resItem.setGangs(areaGangs);
			resItem.setAreaName(item.getName());
			resItem.setCreateTime(item.getCreateTime());
			resItem.setCreateUserId(item.getCreateUser());
			resItem.setOffice(Const.TRUE_STRING.equals(item.getOffice()) ? "已认证" : "未认证");
			result.add(resItem);
		});

		return result;
	}

	public SummaryMetaData getSummaryMeta(Integer summaryId,Integer enemyId) {
		SummaryDataRegDTO summaryData = querySummaryData(summaryId);
		if (summaryData == null) {
			return null;
		}
		SummaryMetaData res = new SummaryMetaData();
		RoundCalander cal = new RoundCalander(summaryData.getUploadDate());
		if(enemyId==null){
			enemyId = findEnemyId(cal, summaryData);
		}
		res.setSid(summaryData.getId());
		res.setEid(enemyId);
		res.setYear(cal.getYear());
		res.setWeek(cal.getWeek());
		res.setRound(cal.getRound());
		res.setDateb(cal.getDataBandStr());
		return res;
	}
	public RoundSummaryData findSummaryData(Integer summaryId) {
		SummaryDataRegDTO summaryData = querySummaryData(summaryId);
		if (summaryData == null) {
			return null;
		}
		RoundCalander cal = new RoundCalander(summaryData.getUploadDate());
		RoundSummaryData res = new RoundSummaryData();
		res.setPrimary(translate(summaryData));
		res.setEnemy(translate(getEnemySummary(cal, summaryData)));
		return res;
	}

	/**
	 * 查询指定汇总数据
	 * 
	 * @param sid
	 * @param eid
	 * @return
	 */
	public RoundSummaryData selectSummaryData(Integer sid, Integer eid) {
		if (sid == null) {
			return null;
		}
		RoundSummaryData result = new RoundSummaryData();
		if (eid == null) {
			result.setPrimary(translate(this.querySummaryData(sid)));
			return result;
		}

		List<SummaryDataRegDTO> list = queryMutiSummaryData(sid, eid);
		if (list.size() == 0) {
			return null;
		}
		if (list.get(0).getId().equals(sid)) {
			result.setPrimary(translate(list.get(0)));
			result.setEnemy(translate(list.get(1)));
		} else {
			result.setPrimary(translate(list.get(1)));
			result.setEnemy(translate(list.get(0)));

		}
		return result;
	}

	private List<SummaryDataRegDTO> queryMutiSummaryData(Integer sid, Integer eid) {
		SummaryDataRegDTOExample example = new SummaryDataRegDTOExample();
		example.createCriteria().andIdIn(Arrays.asList(sid, eid));

		List<SummaryDataRegDTO> list = summaryMapper.selectByExample(example);
		return list;
	}

	public FamilyMetaData findFamily(Integer familyId) {
		FamilyRegDTO fReg = familyMapper.selectByPrimaryKey(familyId);
		if(fReg==null){
			return null;
		}
		FamilyMetaData res = new FamilyMetaData();
		res.setFamilyId(fReg.getId());
		res.setFamilyName(fReg.getName());
		res.setCreateTime(fReg.getCreateTime());
		res.setCreateUserId(fReg.getCreateUser());
//		res.setCount(count);TODO
		RoundCalander cal = new RoundCalander();
		res.setYear(cal.getYear());
		res.setWeek(cal.getWeek());
		res.setRound(cal.getRound());
		res.setDateb(cal.getDataBandStr());
		return res;
	}

	public List<Family> findFamilyList() {
		FamilyRegDTOExample example = new FamilyRegDTOExample();
		List<FamilyRegDTO> list = familyMapper.selectByExample(example);
		return translateFamilyList(list);
	}

	private List<Family> translateFamilyList(List<FamilyRegDTO> list) {
		if(list.isEmpty()){
			return null;
		}
		List<Family> result = new ArrayList<>();
		list.forEach(item->{
			Family f = new Family();
			f.setId(item.getId());
			f.setName(item.getName());
			f.setCreateUserId(item.getCreateUser());
			f.setCreateTime(item.getCreateTime());
//			f.setCount(count); TODO
			result.add(f);
		});
		return result;
	}

}
