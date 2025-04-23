package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterHttps extends OncePerRequestFilter
{
	Environment env;
	
	
	public FilterHttps(Environment env)
	{
		this.env = env;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		String protocol = request.getHeader("X-Forwarded-Proto");
		System.out.println("Order: HIGHEST, xForwardedProto : "+protocol+", method : "+request.getMethod()+", URL : "+request.getRequestURL());
		if ("https".equals(protocol))
		{
			System.out.println("filter Https passed");
			filterChain.doFilter(request, response);
			return;
		}
		if (request.getRequestURI().startsWith("/yresError") || request.getRequestURI().equals("/healthcheck"))
		{
			System.out.println("filter Https skipped");
			filterChain.doFilter(request, response);
			return;
		}
		
		try
		{
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("")
								.scheme("https")
								.host(env.getRequiredProperty("SERY_WEB_DOMAIN"));
			
			if (!request.getMethod().equals("GET"))
			{
				System.out.println("failed: redirect to HTTPS Home");
				response.sendRedirect(builder.build().toUriString());
				return;
			}
			
			System.out.println("failed: redirect to HTTPS requested path");
			builder.path(request.getRequestURI());
			if (request.getQueryString()!=null)
				builder.query(request.getQueryString());
			System.out.println("Built URL: "+builder.build().toUriString());
			response.sendRedirect(builder.build().toUriString());
			return;
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(response, new SomethingWentWrongException("filterhttps-01: Configuration Error."));
		}
		return;
	}
}






