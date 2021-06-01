package com.xys.atcrowdfunding.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xys.atcrowdfunding.bean.TAdmin;
import com.xys.atcrowdfunding.bean.TMenu;
import com.xys.atcrowdfunding.service.TAdminService;
import com.xys.atcrowdfunding.service.TMenuService;
import com.xys.atcrowdfunding.util.Const;

@Controller
public class DispatcherController {
	
	Logger log=LoggerFactory.getLogger(DispatcherController.class);
	
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TMenuService menuService;
	
	@RequestMapping("/index")
	public String index()
	{
		log.debug("跳转到系统主页面");
		return "index";
	}
	
	@RequestMapping("/toLogin")
	public String login()
	{
		log.debug("跳转到登录主页面");
		return "login";
	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session)
//	{
//		log.debug("注销系统");
//		if(session!=null) {
//			session.removeAttribute(Const.LOGIN_ADMIN);
//			session.invalidate();
//		}
//		return "redirect:/index";
//	}
	
	@RequestMapping("/main")
	public String main(HttpSession session)
	{
		log.debug("跳转到后台系统main页面");
		if(session==null)
		{
			return "redirect:/toLogin";
		}
		List<TMenu> menuList=(List<TMenu>) session.getAttribute("menuList");
		if(menuList==null) {
			menuList=menuService.listMenuAll();
			session.setAttribute("menuList", menuList);
		}
		return "main";
	}
	
//	@RequestMapping("/doLogin")
//	public String doLogin(String loginacct,String userpswd, HttpSession session,Model model)
//	{
//		log.debug("开始登录...");
//		log.debug("loginacct={},userpswd={}",loginacct,userpswd);
//		
//		Map<String,Object> paramMap=new HashMap<String,Object>();
//		paramMap.put("loginacct",loginacct);
//		paramMap.put("userpswd",userpswd);
//		
//		try {
//			TAdmin admin=adminService.getAdminByLogin(paramMap);
//			session.setAttribute(Const.LOGIN_ADMIN,admin);
//			log.debug("登录成功");
////			return "main";
//			return "redirect:/main";
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.debug("登录失败={}",e.getMessage());
//			model.addAttribute("message",e.getMessage());
//			return "login";
//		}
//		
//	}

}
