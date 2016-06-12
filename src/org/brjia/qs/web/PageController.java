package org.brjia.qs.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brjia.qs.model.AreaRegDTO;
import org.brjia.qs.model.MemberRegDTO;
import org.brjia.qs.round.RoundCalander;
import org.brjia.qs.service.FightingService;
import org.brjia.qs.service.UserService;
import org.brjia.qs.util.AreaGang;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 页面控制器
 * @author hujiawei
 *
 */
@Controller
public class PageController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private FightingService fightingService;
	 /**
	  * 主页
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "/index",method = RequestMethod.GET)
	 public String home(HttpServletRequest req,ModelMap modelMap) throws IOException {
		 RoundCalander cal = new RoundCalander();
		 modelMap.put("year", cal.getYear());
		 modelMap.put("week", cal.getWeek());
		 modelMap.put("round", cal.getRound());
		 modelMap.put("dateb", cal.getDataBandStr());
		 modelMap.put("uploads", fightingService.findUploadInfo());
		 return "index";
	 }
	 @RequestMapping(value = "/welcome.html",method = RequestMethod.GET)
	 public String welcome(HttpServletRequest req,ModelMap modelMap) throws IOException {
		 RoundCalander cal = new RoundCalander();
		 modelMap.put("year", cal.getYear());
		 modelMap.put("week", cal.getWeek());
		 modelMap.put("round", cal.getRound());
		 modelMap.put("dateb", cal.getDataBandStr());
		 modelMap.put("uploads", fightingService.findUploadInfo());
		 return "index";
	 }
	
	 /**
	  * 上传
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "/upload",method = RequestMethod.GET)
	 public String upload(HttpServletRequest req,ModelMap modelMap) throws IOException {
		 return "upload";
	 }
	 /**
	  * 关于本站
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "/about",method = RequestMethod.GET)
	 public String about(HttpServletRequest req,ModelMap modelMap) throws IOException {
		 return "about";
	 }
	 @RequestMapping(value = "/register",method = RequestMethod.GET)
	 public String register(HttpServletRequest req,ModelMap modelMap) throws IOException {
		 List<MemberRegDTO> memberList = userService.selectMemberList();
		 List<AreaRegDTO> areaList = userService.selectAreaList();
		 modelMap.put("memberList", memberList);
		 modelMap.put("areaList", areaList);
		 return "register";
	 }

}
