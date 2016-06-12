package org.brjia.qs.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.brjia.qs.service.FightingService;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.ResultMessage;
import org.brjia.qs.util.SummaryMetaData;
import org.brjia.qs.util.UploadMetaData;
import org.brjia.qs.util.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 帮会数据控制器
 * 
 * @author hujiawei
 *
 */
@Controller
@RequestMapping("gang")
public class GangController {
	@Resource
	private FightingService fightingService;
	/**
	 * 服务器帮会主页
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String areaIndex(HttpSession ses,ModelMap modelMap) throws IOException {
		UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("areaGangs", fightingService.findGangList());
		return "areagang";
	}
	 
	 /**
	 * 跳转到目标帮会
	 * @return 
	 */
	@RequestMapping(value="/{gangId:\\d*}",method=RequestMethod.GET)
	public String outlineData(@PathVariable Integer gangId,HttpSession ses,ModelMap modelMap) {
		SummaryMetaData summaryID = fightingService.findLastSummaryID(gangId);
		if(summaryID==null){
			modelMap.addAttribute("msg_code", Const.MSG_NO_SUMMARY);
			return "msg";
		}
		modelMap.addAttribute("user", ses.getAttribute(Const.USER_KEY));
		modelMap.addAttribute("smeta", summaryID);
//		modelMap.addAttribute("uploads", fightingService.findUploadInfo(gangId));
		return "gang";
		
	}
	@RequestMapping(value="/{gangId:\\d*}/{summaryId:\\d*}",method=RequestMethod.GET)
	public String outlineData(@PathVariable Integer summaryId,@RequestParam(required=false) Integer enemyId,HttpSession ses,ModelMap modelMap) {
		SummaryMetaData summaryID = fightingService.getSummaryMeta(summaryId,enemyId);
		if(summaryID==null){
			modelMap.addAttribute("msg_code", Const.MSG_NO_SUMMARY);
			return "msg";
		}
		modelMap.addAttribute("user", ses.getAttribute(Const.USER_KEY));
		modelMap.addAttribute("smeta", summaryID);
		return "gang";
		
	}
	@RequestMapping(value="/upload",method=RequestMethod.GET)
	@ResponseBody
	public ResultMessage uploadData(@RequestParam Integer gangId,HttpSession ses,ModelMap modelMap) {
		ResultMessage rs = new ResultMessage();
		List<UploadMetaData> uploads = fightingService.findUploadInfo(gangId);
		if(uploads==null){
			rs.setMsg("暂无近期数据");
			return rs;
		}
		rs.setData(uploads);
		return rs;
		
	}
	
}
