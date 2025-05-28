package com.sunya.yresWebProject.accountSecurity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.managers.SessionManager;

@Component
public class CheckRole
{
	@Autowired
	private RoleHierarchy roleHierar;
	@Autowired
	private SessionManager sm;


	public boolean hasAuthority(String authority)
	{
		Authentication auth;
		if (sm.getSecurityContext()==null)
			auth = SecurityContextHolder.getContext().getAuthentication();
		else
			auth = sm.getSecurityContext().getAuthentication();
		
		if (authority==null || !auth.isAuthenticated())
		{
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = roleHierar
									.getReachableGrantedAuthorities(auth.getAuthorities());
		return authorities.stream().anyMatch(author -> authority.equals(author.getAuthority()));
	}


	public boolean isAuthenticated()
	{
		Authentication auth = sm.getSecurityContext().getAuthentication();
		return auth.isAuthenticated();
	}

}
