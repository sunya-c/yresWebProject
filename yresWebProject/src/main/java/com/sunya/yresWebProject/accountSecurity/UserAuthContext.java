package com.sunya.yresWebProject.accountSecurity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.sunya.yresWebProject.managers.SessionManager;

@Component
public class UserAuthContext
{
	@Autowired
	private RoleHierarchy roleHierar;
	@Autowired
	private SessionManager sm;


	public boolean hasAuthority(String authority)
	{
		Authentication auth;
		Collection<? extends GrantedAuthority> reachableAuthorities;
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			if (sm.getSecurityContext()==null)
				auth = SecurityContextHolder.getContext().getAuthentication();
			else
				auth = sm.getSecurityContext().getAuthentication();
			
			if (authority==null || auth==null || !auth.isAuthenticated())
			{
				return false;
			}
			reachableAuthorities = roleHierar.getReachableGrantedAuthorities(auth.getAuthorities());
		}
		return reachableAuthorities.stream().anyMatch(author -> authority.equals(author.getAuthority()));
	}

	public boolean isAuthenticated()
	{
		return hasAuthority("ROLE_USER");
	}
	
	public boolean isAdmin()
	{
		return hasAuthority("ROLE_ADMIN");
	}
	
	public String getUsername()
	{
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			Authentication auth;
			if (sm.getSecurityContext()==null)
				auth = SecurityContextHolder.getContext().getAuthentication();
			else
				auth = sm.getSecurityContext().getAuthentication();
			
			if (auth==null)
				return null;
			
			return auth.getName();
		}
	}
	
	public String getUsernameEscaped()
	{
		String username = getUsername();
		return (username==null)? null : HtmlUtils.htmlEscape(username);
	}
	
	public void printAllAthor()
	{
		Authentication auth;
		if (sm.getSecurityContext()==null)
			auth = SecurityContextHolder.getContext().getAuthentication();
		else
			auth = sm.getSecurityContext().getAuthentication();
		
		Collection<? extends GrantedAuthority> reachAthor = roleHierar.getReachableGrantedAuthorities(auth.getAuthorities());
		auth.getAuthorities().forEach(e -> System.err.println(e.toString()));
		System.err.println("^athor, v reachableAthor");
		reachAthor.forEach(e -> System.err.println(e.toString()));
	}
}
