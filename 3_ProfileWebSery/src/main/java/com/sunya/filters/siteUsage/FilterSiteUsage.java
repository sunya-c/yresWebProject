package com.sunya.filters.siteUsage;

import java.io.IOException;
import java.sql.SQLException;

import com.sunya.PrintError;
import com.sunya.daos.DaoSiteUsage;
import com.sunya.managers.CookieManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterSiteUsage
{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private int filterNumber;
	private DaoSiteUsage dao;
	private String refNumber;
	private int[] updatedUsage;
	
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, int filterNumber, Object obj)
			throws ServletException, IOException
	{
		this.request = request;
		this.response = response;
		this.filterNumber = filterNumber;
		
		dao = new DaoSiteUsage();
		
		CookieManager cm = new CookieManager(request.getCookies());
		refNumber = cm.getCookieValue(cm.CLIENT_REF);
		
		try
		{
			setUpdatedUsage();
			String result = dao.updateUsage(refNumber, updatedUsage);
			
			if (filterNumber == 1 || filterNumber == 3)
				checkOutcome(result, cm, filterChain, request.getRemoteAddr());
			else
				checkOutcome(result, cm, filterChain);
		}
		catch (IOException | ServletException | SQLException e)
		{
			PrintError.toErrorPage(request.getSession(), response, obj, e);
		}
	}
	
	
	private void setUpdatedUsage() throws ServletException, SQLException
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
		
		updatedUsage[filterNumber] += 1;
	}
	
	/**
	 * For FeedbackPage (filterNumber 2)
	 * 
	 * @param result
	 * @param cm
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void checkOutcome(String result, CookieManager cm, FilterChain filterChain) throws IOException, ServletException
	{
		if (result != null)
		{
			setupRefInCookie(cm, result);
			filterChain.doFilter(request, response);
		}
		else
		{
			throw new ServletException("filtersiteusage.checkoutcome-01: SiteUsage update failed.");
		}
	}
	
	/**
	 * For HomePage (filterNumber 3) and ErrorPage (filterNumber 1)
	 * 
	 * @param result
	 * @param cm
	 * @param filterChain
	 * @param ip
	 * @throws IOException
	 * @throws ServletException
	 * @throws SQLException 
	 */
	private void checkOutcome(String result, CookieManager cm, FilterChain filterChain, String ip) throws IOException, ServletException, SQLException
	{
		if (filterNumber == 1)
		{
			dao.addIp(ip, result);
			
			setupRefInCookie(cm, result);
			filterChain.doFilter(request, response);
		}
		else if (result != null)
		{
			if ( dao.addIp(ip, result) == null )
			{
				refNumber = null;
				setUpdatedUsage();
				result = dao.updateUsage(refNumber, updatedUsage);
				checkOutcome(result, cm, filterChain, ip);
			}
			else
			{
				setupRefInCookie(cm, result);
				filterChain.doFilter(request, response);
			}
		}
		else
		{
			throw new ServletException("filtersiteusage.checkoutcome-02: SiteUsage update failed.");
		}
	}
	
	private void setupRefInCookie(CookieManager cm, String result)
	{
		Cookie cookie = new Cookie(cm.CLIENT_REF, result);
		cookie.setMaxAge(7*24*60*60);
		response.addCookie(cookie);
	}
}
