package org.brjia.qs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.brjia.qs.util.Const;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewBaseDataInterceptor extends HandlerInterceptorAdapter {

	private static Logger _log = Logger.getLogger(ViewBaseDataInterceptor.class);
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		if(modelAndView!=null){
			modelAndView.addObject("root", request.getServletContext().getContextPath());
			modelAndView.addObject("user", request.getSession().getAttribute(Const.USER_KEY));
			if(_log.isInfoEnabled()){
				_log.info("attach path and user info to view:"+modelAndView.getViewName());
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
