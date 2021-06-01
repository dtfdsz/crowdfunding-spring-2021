package com.xys.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xys.atcrowdfunding.bean.TRole;
import com.xys.atcrowdfunding.service.TRoleService;

@Controller
public class TRoleController {

	@Autowired
	TRoleService roleService;
	
	Logger log=LoggerFactory.getLogger(TAdminController.class);
	
	@RequestMapping("/role/index")
	public String index() {
		log.debug("跳转到role/index.....");
		return "role/index";
	}
	

	@PreAuthorize("hasRole('PM - 项目经理')")
	@ResponseBody
	@RequestMapping("/role/doAdd")
	public String doAdd(TRole role) {
		roleService.saveTRole(role);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/role/doUpdate")
	public String doUpdate(TRole role) {
		roleService.updateTRole(role);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/role/doDelete")
	public String doDelete(Integer id) {
		log.debug("跳转到role/delete.....");
		roleService.deleteTRole(id);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/role/getRoleById")
	public TRole getRoleById(Integer id) {
		log.debug("跳转到role/getRoleById.....");
		return roleService.getRoleById(id);
	}
	
	
	@ResponseBody
	@RequestMapping("/role/loadData")
	public PageInfo<TRole> loadData(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
							@RequestParam(value="pageSize",required=false,defaultValue="5")Integer pageSize,
							@RequestParam(value="condition",required=false,defaultValue="")String condition) {
		
		PageHelper.startPage(pageNum,pageSize);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		paramMap.put("condition", condition);
		
		PageInfo<TRole> page=roleService.listRolePage(paramMap);
		return page;
	}
}
