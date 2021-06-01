package com.xys.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.xys.atcrowdfunding.bean.TRole;
import com.xys.atcrowdfunding.bean.TRoleExample;
import com.xys.atcrowdfunding.mapper.TAdminRoleMapper;
import com.xys.atcrowdfunding.mapper.TRoleMapper;
import com.xys.atcrowdfunding.service.TRoleService;

@Service
public class TRoleServiceImpl implements TRoleService {

	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TAdminRoleMapper adminRoleMapper;

	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {
		
		String condition=(String)paramMap.get("condition");
		
		List<TRole> list=null;
		if(StringUtils.isEmpty(condition))
		{
			list=roleMapper.selectByExample(null);
		}
		else {
			TRoleExample example=new TRoleExample();
			example.createCriteria().andNameLike("%"+condition+"%");
			list=roleMapper.selectByExample(example);
		}
		
		PageInfo<TRole> page=new PageInfo<TRole>(list,5);
		return page;
	}
	
//	@PreAuthorize("PM - 项目经理")
	@Override
//	@PreAuthorize("hasRole('PM - 项目经理')")
	public void saveTRole(TRole role) {
		roleMapper.insertSelective(role);
		
	}

	@Override
	public TRole getRoleById(Integer id) {
		
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTRole(TRole role) {
		
		roleMapper.updateByPrimaryKeySelective(role);		
	}

	@Override
	public void deleteTRole(Integer id) {
		
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TRole> listAllRole() {
		
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> getRoleIdByAdminId(String id) {
		
		return adminRoleMapper.getRoleIdByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.saveAdminAndRoleRelationship(roleId,adminId);
		
	}
}
