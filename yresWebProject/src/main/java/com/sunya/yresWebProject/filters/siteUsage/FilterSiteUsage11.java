package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.PageUsageinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for <strong>AdminPanelPage</strong>.<br>
 * <br>
 * This filter is for incrementing the number of usage in the database.
 */
public class FilterSiteUsage11 extends OncePerRequestFilter
{
	private FilterSiteUsage siteUsage;
	
	
	public FilterSiteUsage11(FilterSiteUsage siteUsage)
	{
		this.siteUsage = siteUsage;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order: 5, in Filter Usage 11 (adminPanel)");
		siteUsage.doFilterInternal(request, response, filterChain, PageUsageinfo.PAGE_ADMINPANEL);
	}
	
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
