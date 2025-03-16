package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.PageUsageinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for FeedbackPage
 */
public class FilterSiteUsage2 extends OncePerRequestFilter
{
	FilterSiteUsage siteUsage;
	
	public FilterSiteUsage2(FilterSiteUsage siteUsage)
	{
		this.siteUsage = siteUsage;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 6, in Filter Usage 2 (feedback)");
		siteUsage.doFilterInternal(request, response, filterChain, PageUsageinfo.PAGE_FEEDBACK, this);
	}



	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
