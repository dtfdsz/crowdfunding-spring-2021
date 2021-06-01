package com.xys.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xys.atcrowdfunding.bean.TRole;

public interface TRoleService {

	PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

	void saveTRole(TRole role);

	TRole getRoleById(Integer id);

	void updateTRole(TRole role);

	void deleteTRole(Integer id);

	List<TRole> listAllRole();

	List<Integer> getRoleIdByAdminId(String id);

	void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId);

}
