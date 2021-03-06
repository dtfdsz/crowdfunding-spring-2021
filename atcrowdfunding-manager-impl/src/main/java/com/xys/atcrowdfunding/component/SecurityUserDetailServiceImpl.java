package com.xys.atcrowdfunding.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.xys.atcrowdfunding.bean.TAdmin;
import com.xys.atcrowdfunding.bean.TAdminExample;
import com.xys.atcrowdfunding.bean.TPermission;
import com.xys.atcrowdfunding.bean.TRole;
import com.xys.atcrowdfunding.mapper.TAdminMapper;
import com.xys.atcrowdfunding.mapper.TPermissionMapper;
import com.xys.atcrowdfunding.mapper.TRoleMapper;


@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	TAdminMapper adminMapper;
	
	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TPermissionMapper permissionMapper;
	
	Logger log=LoggerFactory.getLogger(SecurityUserDetailServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//1.查询用户对象
		log.debug("查询用户对象......");
		TAdminExample example=new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list=adminMapper.selectByExample(example);
		
		if(list!=null && list.size()==1) {
			TAdmin admin=list.get(0);
			Integer adminId=admin.getId();
			
			log.debug("用户信息：{}",admin);
		
			//2.查询角色集合
			List<TRole> roleList=roleMapper.listRoleByAdminId(adminId);
			log.debug("用户角色：{}",roleList);
		
			//3.查询权限集合
			List<TPermission> permissionList=permissionMapper.listPermissionByAdminId(adminId);
 		
			//4.构建用户所有权限集合
			Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
			
			log.debug("用户权限集合：{}",authorities);
			
			for(TRole role:roleList) {
				
				authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			}
			
			for(TPermission permission:permissionList) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
			
//			return new User(admin.getLoginacct(),admin.getUserpswd(),authorities);
			
			return new TSecurityAdmin(admin,authorities);
		}
		else {
		return null;
		}
	}

}
