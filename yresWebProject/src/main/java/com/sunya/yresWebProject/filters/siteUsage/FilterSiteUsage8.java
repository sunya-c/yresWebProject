package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.PageUsageinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for <strong>sDownloadResume</strong>.<br>
 * <br>
 * This filter is for incrementing the number of usage in the database.
 */
public class FilterSiteUsage8 extends OncePerRequestFilter
{
	private FilterSiteUsage siteUsage;
	
	public FilterSiteUsage8(FilterSiteUsage siteUsage)
	{
		this.siteUsage = siteUsage;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 5, in Filter Usage 8 (sDownloadResume)");
		siteUsage.doFilterInternal(request, response, filterChain, PageUsageinfo.RESUME_DOWNLOAD);
	}



	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
