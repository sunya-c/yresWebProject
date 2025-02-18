package com.sunya.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.PrintError;
import com.sunya.managers.SessionManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FilterLoginState extends OncePerRequestFilter
{
	final private String ERR1 = "Filter LoginState failed";
	private String errText = "";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 3, in Filter Login State (welcome)");
		
		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		
		if (
				(session.getAttribute(sm.LOGIN_LOGGED_IN) != null) &&
				((boolean) session.getAttribute(sm.LOGIN_LOGGED_IN) == true)
				)
		{
			System.out.println("Filter Login State passed");
			filterChain.doFilter(request, response);
		}
		else
		{
			errText = ERR1;
			PrintError.println(errText);
			response.sendRedirect("Home");
		}

	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
