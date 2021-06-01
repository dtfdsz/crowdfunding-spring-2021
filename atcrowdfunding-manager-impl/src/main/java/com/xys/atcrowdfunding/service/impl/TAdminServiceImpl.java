package com.xys.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.xys.atcrowdfunding.bean.TAdmin;
import com.xys.atcrowdfunding.bean.TAdminExample;
import com.xys.atcrowdfunding.bean.TAdminExample.Criteria;
import com.xys.atcrowdfunding.exception.LoginException;
import com.xys.atcrowdfunding.mapper.TAdminMapper;
import com.xys.atcrowdfunding.service.TAdminService;
import com.xys.atcrowdfunding.util.AppDateUtils;
import com.xys.atcrowdfunding.util.Const;

@Service
public class TAdminServiceImpl implements TAdminService{
	
	@Autowired	
	TAdminMapper adminMapper;

	@Override
	public TAdmin getAdminByLogin(Map<String, Object> paramMap) {

		//1.密码加密
		
		//2.查询用户
		String loginacct=(String)paramMap.get("loginacct");
		String userpswd=(String)paramMap.get("userpswd");
		TAdminExample example=new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TAdmin> list=adminMapper.selectByExample(example);
		
		//3.判断用户是否为null
		if (list==null || list.size()==0)
		{
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}
		
		//4.判断密码是否一致
		TAdmin admin=list.get(0);
		if(!admin.getUserpswd().equals(userpswd))
		{
			throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
		}
		
		//5.返回结果
	
		return admin;
	}

	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {
		
		TAdminExample example=new TAdminExample();
		
		String condition=(String)paramMap.get("condition");
		
		if(!"".equals(condition))
		{
			Criteria criteria1 = example.createCriteria();
			criteria1.andLoginacctLike("%"+condition+"%");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andLoginacctLike("%"+condition+"%");
			
			Criteria criteria3 = example.createCriteria();
			criteria3.andLoginacctLike("%"+condition+"%");
			
			example.or(criteria1);
			example.or(criteria2);
			example.or(criteria3);
		}
		
		
//		example.setOrderByClause("createtime desc");
		
		List<TAdmin> list=adminMapper.selectByExample(example);
		
		PageInfo<TAdmin> page=new PageInfo<TAdmin>(list,5);
		
		return page;
	}

	@Override
	public void saveTAdmin(TAdmin admin) {
		// TODO Auto-generated method stub
		admin.setUserpswd(Const.DEFAULT_USERPSWD);
		admin.setCreatetime(AppDateUtils.getFormatTime());
		adminMapper.insertSelective(admin);
	}

	@Override
	public TAdmin getTAdminById(Integer id) {
		
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTAdmin(TAdmin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
		
	}

	@Override
	public void deleteTAdmin(Integer id) {
		
		adminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> idList) {
		// TODO Auto-generated method stub
		
		adminMapper.deleteBatch(idList);
	}

}
