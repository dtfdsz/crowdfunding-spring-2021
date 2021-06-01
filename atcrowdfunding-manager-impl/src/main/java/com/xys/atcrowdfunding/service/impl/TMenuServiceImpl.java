package com.xys.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xys.atcrowdfunding.bean.TMenu;
import com.xys.atcrowdfunding.mapper.TMenuMapper;
import com.xys.atcrowdfunding.service.TMenuService;

@Service
public class TMenuServiceImpl implements TMenuService {

	Logger log=LoggerFactory.getLogger(TMenuServiceImpl.class);
	
	@Autowired
	TMenuMapper menuMapper;

	@Override
	public List<TMenu> listMenuAll() {

		List<TMenu> menuList=new ArrayList<TMenu>();
		Map<Integer,TMenu> cache=new HashMap<Integer,TMenu>();
		
		List<TMenu> allList=menuMapper.selectByExample(null);
		
		for(TMenu tMenu:allList)
		{
			
				if(tMenu.getPid()==0)
				{
					menuList.add(tMenu);
					cache.put(tMenu.getId(), tMenu);
				}
			
		}
		
		for(TMenu tMenu:allList)
		{
			if(tMenu.getPid()!=0)
			{
				Integer pid=tMenu.getPid();
				TMenu parent=cache.get(pid);
				parent.getChildren().add(tMenu);
			}
		}

		log.debug("菜单={}",menuList);
		
		return menuList;
	}

	@Override
	public List<TMenu> listMenuAllTree() {
		
		return menuMapper.selectByExample(null);
	}

	@Override
	public void saveTMenu(TMenu menu) {
		
		menuMapper.insertSelective(menu);
		
	}

	@Override
	public TMenu getMenuById(Integer id) {
		
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTMenu(TMenu menu) {
		
		menuMapper.updateByPrimaryKeySelective(menu);
		
	}

	@Override
	public void deleteTMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
		
	}
}
