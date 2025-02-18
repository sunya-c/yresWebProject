package com.sunya.filters.siteUsage;

import java.io.IOException;

import com.sunya.PrintError;
import com.sunya.daos.DaoSiteUsage;
import com.sunya.managers.CookieManager;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FilterSiteUsage3 extends HttpFilter implements Filter
{
	HttpServletRequest req;
	HttpServletResponse res;
	
	int[] updatedUsage;
	String refNumber;
	DaoSiteUsage dao;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		System.out.println("in Filter Site Usage 3 : Home/Login");
		req = (HttpServletRequest) request;
		res = (HttpServletResponse) response;

		dao = new DaoSiteUsage();
		
		CookieManager cm = new CookieManager(req.getCookies());
		refNumber = cm.getCookieValue(cm.CLIENT_REF);
		
		
		try
		{
			String result = updateUsage();
			checkOutcome(result, request.getRemoteAddr(), cm, chain);
		}
		catch (IOException e)
		{
			PrintError.toErrorPage(req.getSession(), res, this, e);
		}
		catch (ServletException e)
		{
			PrintError.toErrorPage(req.getSession(), res, this, e);
		}
	}
	
	
	private String updateUsage() throws ServletException
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
		
		updatedUsage[3] += 1;
		
		return dao.updateUsage(refNumber, updatedUsage);
	}
	
	
	private void checkOutcome(String result, String ip, CookieManager cm, FilterChain chain) throws IOException, ServletException
	{
		if (result == null)
		{
			try
			{
				throw new ServletException("SiteUsage update failed.");
			}
			catch (ServletException e)
			{
				PrintError.toErrorPage(req.getSession(), res, this, e);
			}
		}
		else
		{
			if ( dao.addIp(ip, result) == null )
			{
				refNumber = null;
				result = updateUsage();
				checkOutcome(result, ip, cm, chain);
			}
			else
			{
				Cookie cookie = new Cookie(cm.CLIENT_REF, result);
				cookie.setMaxAge(7*24*60*60);
				res.addCookie(cookie);
				chain.doFilter(req, res);
			}
		}
	}
}
