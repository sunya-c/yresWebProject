package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterInitializeSession extends OncePerRequestFilter
{
	private SessionManager sm;
	
	public FilterInitializeSession(SessionManager sm)
	{
		this.sm = sm;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		synchronized (sm.getSession())
		{
			if (sm.getInitializeString()==null)
			{
				System.out.println("in Filter Initizlize. Should be run once at the beginning.------------------------------------------------------------once");
				sm.createKeyHolder();
				sm.createSessionFeedback();
				sm.createSessionCreateAccount();
				sm.createSessionLogin();
				sm.createSessionWeb();
				sm.createSessionRedirecting();
				
				sm.setSessionInitialized();
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	
}
