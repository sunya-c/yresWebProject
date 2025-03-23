package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterLoginState extends OncePerRequestFilter
{
	private final String ERR1 = "Filter LoginState failed";
	
	private SessionManager sm;
	
	
	
	public FilterLoginState(SessionManager sm)
	{
		this.sm = sm;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 3, in Filter Login State (welcome)");
		
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			if (sm.getSessionLogin().isLoggedIn())
			{
				System.out.println("Filter Login State passed");
				filterChain.doFilter(request, response);
			}
			else
			{
				PrintError.println(ERR1);
				response.sendRedirect(Url.home);
			}
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
