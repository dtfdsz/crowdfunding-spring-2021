package com.xys.atcrowdfunding.service;

import java.util.List;

import com.xys.atcrowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listMenuAll();//组合父子关系
	
	List<TMenu> listMenuAllTree();//不用组合父子关系

	void saveTMenu(TMenu menu);

	TMenu getMenuById(Integer id);

	void updateTMenu(TMenu menu);

	void deleteTMenu(Integer id);

}
