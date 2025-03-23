package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.PageUsageinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for PersInfoPage
 */
public class FilterSiteUsage4 extends OncePerRequestFilter
{
	private FilterSiteUsage siteUsage;
	
	public FilterSiteUsage4(FilterSiteUsage siteUsage)
	{
		this.siteUsage = siteUsage;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 8, in Filter Usage 4 (personalInformation)");
		siteUsage.doFilterInternal(request, response, filterChain, PageUsageinfo.PAGE_PERSINFO, this);
	}



	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
