package org.brjia.qs.mapper;

import java.util.Map;

import org.brjia.qs.model.SummaryDataRegDTO;

public interface FightingSummaryMapper {

	void insert(SummaryDataRegDTO statistic);

	/**
	 * 加入敌帮id
	 * @param id
	 * @param enemy
	 */
	void updateEnemy(Integer id, Integer enemy);

	SummaryDataRegDTO select(Integer summaryID);

	SummaryDataRegDTO selectByMap(Map<String, Object> arg);

	/**
	 * 查询敌对帮会的汇总id <br/>
	 * 关于参数，特别注意：gangId非等值，其它条件为等值
	 * @param arg
	 * @return
	 */
	Integer selectEnemyIdByMap(Map<String, Object> arg);

	SummaryDataRegDTO selectLastByGang(Integer gangId);

	SummaryDataRegDTO findSummary(int year, int week, int round, String gangName, Integer areaId, String signature);

}
