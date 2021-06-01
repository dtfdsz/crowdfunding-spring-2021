package com.xys.atcrowdfunding.service;


import java.util.List;

import com.xys.atcrowdfunding.bean.TPermission;

public interface TPermissionService {

	List<TPermission> getAllPermissions();

	void savePermission(TPermission permission);

	void deletePermission(Integer id);

	void editPermission(TPermission permission);

	TPermission getPermissionById(Integer id);

	List<TPermission> getPermissionByMenuid(Integer mid);

	void assignPermissionToMenu(Integer mid, List<Integer> perIdArray);
}
