package org.brjia.qs.web;

import javax.annotation.Resource;

import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.round.RoundSummaryData;
import org.brjia.qs.service.FightingService;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.ResultMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帮会数据控制器
 * 
 * @author hujiawei
 *
 */
@RestController
@RequestMapping("round")
public class RoundController {
	@Resource
	private FightingService fightingService;
	/**
	 * 标题数据
	 */
	public void headData(String id) {
		
	}
	/**
	 * 根据summaryid查询汇总数据
	 * @return 
	 */
	@RequestMapping(value="summary/{sid:\\d*}",method=RequestMethod.GET)
	public ResultMessage outlineData(@PathVariable Integer sid,@RequestParam(required=false) Integer eid) {
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.selectSummaryData(sid,eid));
		return rm;
	}
	/**
	 * 使用默认帮会id查询最近一期汇总数据
	 * @return 
	 */
	@RequestMapping(value="summary/index",method=RequestMethod.GET)
	public ResultMessage indexOutlineData() {
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.findLastSummaryData(Const.DEFAULT_GANG_ID));
		return rm;
	}
	/**
	 * 帮会成员输出和辅助数据
	 * @return 
	 */
	@RequestMapping(value="listdata",method=RequestMethod.GET)
	public ResultMessage memberDataDirect(@RequestParam("sid") Integer sumaryId,@RequestParam(value="eid",required=false) Integer enemyId) {
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.findMemeberListData(sumaryId, enemyId));
		return rm;
	} 
	/**
	 * 英雄汇总数据
	 * @return 
	 */
	@RequestMapping(value="hero",method=RequestMethod.GET)
	public ResultMessage heroSummaryData(@RequestParam("sid") Integer sumaryId,@RequestParam(value="eid",required=false) Integer enemyId) {
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.findHeroData(sumaryId, enemyId));
		return rm;
	}
	/**
	 * 获取已上传数据的帮会列表
	 * @return 
	 */
	@RequestMapping(value = "list/uploads", method = RequestMethod.GET)
	@ResponseBody
	public ResultMessage getUploadGangList() {
		ResultMessage rm = new ResultMessage();
		rm.setData(fightingService.findUploadInfo());
		return rm;
	}
}
