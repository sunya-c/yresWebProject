package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.managers.CookieManager;
import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterSetCookie extends OncePerRequestFilter
{
	@Autowired
	CookieManager cm;
	@Autowired
	SessionManager sm;

	public FilterSetCookie(CookieManager cm, SessionManager sm)
	{
		this.cm = cm;
		this.sm = sm;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order: 1.5, in Filter Set Cookie (/*)");
		try
		{
			Cookie c = cm.createCookie(CookieManager.JSESSION, sm.getSession().getId(), -1);
			response.addCookie(c);
			filterChain.doFilter(request, response);
			return;
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(response, e);
			return;
		}
	}

}
