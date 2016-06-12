package org.brjia.qs.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.brjia.qs.util.Const;
import org.brjia.qs.util.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("my")
public class ProfileController {

	/**
	  * 帮会主页
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "",method = RequestMethod.GET)
	 public String index(HttpSession ses,ModelMap modelMap) throws IOException {
		 UserInfo user = (UserInfo) ses.getAttribute(Const.USER_KEY);
		 if(user==null){
			 modelMap.addAttribute("msg_code", Const.MSG_NO_LOGIN);
			 return "msg";
		 }
		 if(user.getGangId()==null){
			 modelMap.addAttribute("msg_code", Const.MSG_NO_ROLE);
			 return "msg";
		 }
		return "forward:gang/"+user.getGangId();
	 }
}
