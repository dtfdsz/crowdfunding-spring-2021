package com.xys.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xys.atcrowdfunding.bean.TAdmin;

import com.xys.atcrowdfunding.bean.TRole;

import com.xys.atcrowdfunding.service.TAdminService;
import com.xys.atcrowdfunding.service.TRoleService;

@Controller
public class TAdminController {
	
	Logger log=LoggerFactory.getLogger(TAdminController.class);
	
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TRoleService roleService;
	
	
	
	@RequestMapping("/admin/toAdd")
	public String toAdd()
	{
		log.debug("跳转到新增页面admin/toAdd.....");
		return "admin/toAdd";
	}
	
	@PreAuthorize("hasRole('PM - 项目经理')")
	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin admin)
	{
		adminService.saveTAdmin(admin);
		log.debug("跳转到admin/doAdd.....");
		return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
//		return "redirect:/admin/index";
	}
	
	@RequestMapping("/admin/doUpdate")
	public String doUpdate(TAdmin admin,Integer pageNum)
	{
		adminService.updateTAdmin(admin);
		log.debug("跳转到admin/doUpdate.....");
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id,Model model)
	{
		
		log.debug("跳转到admin/toUpdate....");
		TAdmin admin=adminService.getTAdminById(id);
		model.addAttribute("admin",admin);
		return "admin/update";
	}
	
	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id,Integer pageNum)
	{
		adminService.deleteTAdmin(id);
		log.debug("跳转到admin/doDelete.....");
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/doDeleteBatch")
	public String doDeleteBatch(String ids,Integer pageNum)
	{
		List<Integer> idList=new ArrayList<Integer>();
		
		String[] split=ids.split(",");
		
		for(String idStr:split) {
			int id=Integer.parseInt(idStr);
			idList.add(id);
		}
		
		adminService.deleteBatch(idList);
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/toAssign")
	public String toAssign(String id,Model model)
	{
		//1.查询所有角色
		log.debug("跳转到页面/admin/toAssign.....");
		
		List<TRole> allList=roleService.listAllRole();
		
		//2.根据用户id查询已经拥有的角色id
		List<Integer> roleIdList=roleService.getRoleIdByAdminId(id);
		
		//3.将所有角色，进行划分
		List<TRole> assignList=new ArrayList<TRole>();
		List<TRole> unAssignList=new ArrayList<TRole>();
		
		for(TRole role:allList) {
			
			if(roleIdList.contains(role.getId())) {
				assignList.add(role);
				log.debug(role.getName());
			}
			else {
				unAssignList.add(role);
			}
		}
		
		model.addAttribute("assignList",assignList);
		model.addAttribute("unAssignList",unAssignList);
//		model.addAttribute("adminId",id);
		
//		return "redirect:/admin/index?id="+id;
		return "admin/assignRole";
	}
	
//	@RequestMapping("/admin/moveRoleLeftToRight")
//	public String moveRoleLeftToRight(String adminId,String roleIdList)
//	{
//		List<Integer> idList=new ArrayList<Integer>();
//		
//		String[] split=roleIdList.split(",");
//		
//		TAdminRole adminRole=new TAdminRole();
//		
//		for(String str:split)
//		{
//			adminRole.setAdminid(Integer.parseInt(adminId));
//			adminRole.setRoleid(Integer.parseInt(str));
//			adminRoleMapper.insertSelective(adminRole);
//		}
//		
//		return "admin/assignRole";
//	}
	@ResponseBody
	@RequestMapping("/admin/doAssign")
	public String doAssign(Integer[] roleId,Integer adminId) {
		
		log.debug("跳转到/admin/doAssign..........");
		log.debug("adminId:"+adminId);
		
		roleService.saveAdminAndRoleRelationship(roleId,adminId);
		
		return "ok";
	}
	
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
						@RequestParam(value="pageSize",required=false,defaultValue="3")Integer pageSize,
						Model model,
						@RequestParam(value="condition",required=false,defaultValue="")String condition)
	{
		
		log.debug("跳转到admin/index.....");
		
		PageHelper.startPage(pageNum,pageSize);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("condition", condition);
		PageInfo<TAdmin> page=adminService.listAdminPage(paramMap);
		
		model.addAttribute("page",page);
		
		return "admin/index";
	}

}
