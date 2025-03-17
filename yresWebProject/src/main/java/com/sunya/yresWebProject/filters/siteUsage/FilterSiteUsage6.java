package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.PageUsageinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for UnderConstructionPage
 */
public class FilterSiteUsage6 extends OncePerRequestFilter
{
	FilterSiteUsage siteUsage;
	
	public FilterSiteUsage6(FilterSiteUsage siteUsage)
	{
		this.siteUsage = siteUsage;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 10, in Filter Usage 6 (UnderConstructionPage)"); // TODO
		siteUsage.doFilterInternal(request, response, filterChain, PageUsageinfo.PAGE_UNDERCONSTRUCTION, this);
	}



	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
