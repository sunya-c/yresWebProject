package com.sunya.filters.siteUsage;

import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import com.sunya.PrintError;
import com.sunya.daos.DaoSiteUsage;
import com.sunya.managers.CookieManager;

//@WebFilter("/ErrorPage.jsp")
//@Priority(3)
public class FilterSiteUsage1 extends HttpFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		DaoSiteUsage dao = new DaoSiteUsage();
		
		CookieManager cm = new CookieManager(req.getCookies());
		String refNumber = cm.getCookieValue(cm.CLIENT_REF);
		
		int[] updatedUsage;
		
		String result = null;
		try
		{
			if (refNumber == null)
				updatedUsage = new int[dao.getArraySize()];
			else
			{
				updatedUsage = dao.getUsage(refNumber);
				if (updatedUsage == null)
				{
					refNumber = null;
					updatedUsage = new int[dao.getArraySize()];
				}
			}
			
			updatedUsage[1] += 1;
			result = dao.updateUsage(refNumber, updatedUsage);
		}
		catch (Exception e)
		{
		}
		
		if (result != null)
		{
			Cookie cookie = new Cookie(cm.CLIENT_REF, result);
			cookie.setMaxAge(7*24*60*60);
			res.addCookie(cookie);
		}

		try
		{
			chain.doFilter(req, res);
		}
		catch (ServletException e)
		{
		}
	}
}
