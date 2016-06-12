package org.brjia.qs.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.brjia.qs.model.AreaRegDTO;
import org.brjia.qs.service.CollectService;
import org.brjia.qs.service.FightingService;
import org.brjia.qs.service.UserService;
import org.brjia.qs.util.Const;
import org.brjia.qs.util.FileUploadUtil;
import org.brjia.qs.util.QnyhCollectArg;
import org.brjia.qs.util.ResultMessage;
import org.brjia.qs.util.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 站点控制器
 * 
 * @author hujiawei
 *
 */
@Controller
public class SiteController {

	
	@Resource
	private UserService userService;
	@Resource
	private CollectService service;
	
	@RequestMapping(value = "area", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage createArea(@RequestParam("areaName") String name) {
		ResultMessage ms = new ResultMessage();
		AreaRegDTO res = userService.createArea(name);
		if(res==null){
			ms.setMsg("已存在");
		}else{
			ms.setData(res);
		}
		return ms;
	}
	@RequestMapping(value = "username/exist", method = RequestMethod.GET)
	@ResponseBody
	public ResultMessage isExistUser(@RequestParam("email") String name) {
		ResultMessage ms = new ResultMessage();
		ms.setData(userService.isExistUser(name));
		return ms;
	}
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage doLogin(@RequestParam("email") String name, @RequestParam("pwd") String password,HttpSession ses) {
		UserInfo user = userService.findUser(name,password);
		if(user==null){
			return new ResultMessage("密码错误");
		}
		ses.setAttribute(Const.USER_KEY, user);
		ResultMessage ms = new ResultMessage();
		ms.setData(user);
		return ms;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	public ResultMessage doLogout(HttpSession session) {
		session.invalidate();
		ResultMessage rm = new ResultMessage();
		rm.setData(true);
		return rm;
	}

	@RequestMapping(value = "data/register", method = RequestMethod.POST)
	public String doRegister(@RequestParam Map<String, String> args,HttpSession ses) {
		userService.insertUser(args);
		return "redirect:../index";
	}
	@RequestMapping(value = "profile/{pid:\\d*}", method = RequestMethod.GET)
	public String prefile(@PathVariable Integer pid, ModelMap model,HttpSession ses) {
		UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		if(user==null || !user.getUserId().equals(pid)){
			return "error";
		}//TODO 关联角色
		model.addAttribute("user",user);
		model.addAttribute("memberList",userService.selectMemberList());
		//关联－退出家族
		//
		return "profile";
	}
	@RequestMapping(value = "role", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage relateRole(@RequestParam Integer roleId,HttpSession ses) {
		UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		ResultMessage rs = new ResultMessage();
		if(user==null){
			rs.setMsg("未登录");
			return rs;
		}
		if(userService.updateRole(roleId,user)){
			rs.setData(user.getName());
			return rs;
		}
		rs.setMsg("关联失败");
		return rs;
	}

	

	/**
	 * 校验规则 上传：空文件，错误文本类型，总体积，单个文件体积 完整性：文件名时间范围校验，时间格式，内容格式
	 * 重复文件：文件名时间＋内容中的帮会名称＋地区id＋内容签名
	 * 
	 * 上传文件
	 * 
	 * @param fileItems
	 */
	@RequestMapping(value = "data/upload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile[] file,ModelMap model,HttpSession session) {
		String check = FileUploadUtil.check(file);
		if (check != null) {
			model.put("msg", check);
			return "uploadresult";
		}
		List<QnyhCollectArg> list = FileUploadUtil.readString(file);
		check = service.insertFightingSummary((UserInfo)session.getAttribute(Const.USER_KEY), list);
		if (check != null) {
			model.put("msg", check);
			return "uploadresult";
		}
		return "uploadresult";

	}

}
