package com.xys.atcrowdfunding.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xys.atcrowdfunding.bean.TMenu;
import com.xys.atcrowdfunding.service.TMenuService;

@Controller
public class TMenuController {
	
	@Autowired
	TMenuService menuService;
	
	Logger log=LoggerFactory.getLogger(TMenuController.class);

	@RequestMapping("/menu/index")
	public String index()
	{
		return "menu/index";
	}
	
	@ResponseBody
	@RequestMapping("/menu/doAdd")
	public String doAdd(TMenu menu) {
		
		menuService.saveTMenu(menu);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/menu/doUpdate")
	public String doUpdate(TMenu menu) {
		
		menuService.updateTMenu(menu);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/menu/doDelete")
	public String doDelete(Integer id) {
		
		log.debug("跳转到页面menu/doDelete.....");
		menuService.deleteTMenu(id);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/menu/getMenuById")
	public TMenu getMenuById(Integer id) {
		
		TMenu menu=menuService.getMenuById(id);
		return menu;
	}
	
	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree(){
		return menuService.listMenuAllTree();
	}
}
