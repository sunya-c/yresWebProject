package com.sunya.yresWebProject.accountSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

@Service
public class AccountUserDetailsService implements UserDetailsService
{
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private SessionManager sm;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		System.err.println("in IdentityStore");
		sm.clearLoginForm();
		sm.getSessionLogin().setUsernamePreTyped(username);
		ModelLoginInfo model = dao.getPasswordAndRole(username);
		if (model==null)
		{
			sm.getSessionLogin().setUsernameErr("Invalid username!");
			throw new UsernameNotFoundException("Authentication failed.");
		}
		return User.withUsername(username)
					.password(model.getPassword())
					.roles(("0".equals(model.getTempaccount()))? "ADMIN" : "USER")
					.build();
	}
}
