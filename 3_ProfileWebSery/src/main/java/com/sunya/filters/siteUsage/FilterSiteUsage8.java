package com.sunya.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for sDownloadResume
 */
public class FilterSiteUsage8 extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 12, in Filter Usage 8 (sDownloadResume)");
		FilterSiteUsage siteUsage = new FilterSiteUsage();
		siteUsage.doFilterInternal(request, response, filterChain, 8, this);
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
