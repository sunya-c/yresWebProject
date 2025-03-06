package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for WelcomePage
 */
public class FilterSiteUsage7 extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 11, in Filter Usage 7 (welcome)");
		FilterSiteUsage siteUsage = new FilterSiteUsage();
		siteUsage.doFilterInternal(request, response, filterChain, 7, this);
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
