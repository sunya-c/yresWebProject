package com.sunya.yresWebProject.accountSecurity;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomBasicAuthFilter extends AbstractAuthenticationProcessingFilter
{
	private SessionManager sm;
	
	public CustomBasicAuthFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, SessionManager sm)
	{
		super(defaultFilterProcessesUrl, authenticationManager);
		this.sm = sm;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
								throws AuthenticationException, IOException, ServletException
	{
		System.err.println("in Custom Basic AuthFilter");
		try
		{
			SecurityContext context = (SecurityContext)sm.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			if (context!=null && context.getAuthentication()!=null && context.getAuthentication().isAuthenticated())
			{
				return context.getAuthentication();
			}
		}
		catch (ClassCastException e)
		{
			System.err.println("customauthfilter.Class Cast Exception");
		}
		ModelLoginInfo model = new ModelLoginInfo();
		String basicHeader = request.getHeader("Authorization");
		if (basicHeader==null || !basicHeader.startsWith("Basic "))
		{
			throw new BadCredentialsException("Bad credentials (Custom Auth)");
		}
		String basic;
		try
		{
			basic = basicHeader.substring("Basic ".length());
			basic = new String(Base64.getDecoder().decode(basic));
			if (!basic.contains(":"))
				throw new BadCredentialsException("Bad credentials (Custom Auth)");
		}
		catch (Exception e)
		{
			throw new BadCredentialsException("Bad credentials (Custom Auth)");
		}
		String[] usernamePassword = basic.split(":", 2);
		model.setUsername(usernamePassword[0]);
		model.setPassword(usernamePassword[1]);
		
		Authentication auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(auth);
		sm.setSecurityContext(context); // manually save the SecurityContext to the session (Spring doesn't preserve context when using custom filter)
		return auth;
	}
}
