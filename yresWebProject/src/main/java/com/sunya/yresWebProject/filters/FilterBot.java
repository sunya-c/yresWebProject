package com.sunya.yresWebProject.filters;

import java.io.IOException;
import java.time.Duration;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.exceptions.SuspiciousRequestException;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.cache.SimpleCache;
import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterBot extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 1, in Filter Bot (Home)");
		String ip = getIP(request);
		
		IPinfo info = new IPinfo.Builder()
				.setCache(new SimpleCache(Duration.ofDays(30)))
				.build();
		IPResponse lookUp = null;
		
		try
		{
			lookUp = info.lookupIP(ip);
		}
		catch (Exception e)
		{
			//TODO: Send a counter iteration to the database.
			PrintError.toErrorPage(request.getSession(), response, this, new ServletException("filterbot-01: Limit reached."));
		}
		
		String countryCode = lookUp.getCountryCode();
		System.out.println("ip: "+ip);
		System.out.println("code: "+countryCode);
		
		if (countryCode == null || !countryCode.equals("TH"))
		{
			DaoIPBlacklist dao = YresWebProjectApplication.context.getBean(DaoIPBlacklist.class);
			
			try
			{
				if (dao.isBlacklisted(ip))
					dao.increaseCounter(ip);
				else
					dao.addToBlacklist(ip, countryCode);
				
				throw new SuspiciousRequestException("<br>"+ip+"<br>Your request is suspected to be inhuman.<br>If you're a human, "
						+ "please send your intention to visit our website via \'Give feedback / bug report\' button down below.");
			}
			catch (Exception e)
			{
				PrintError.toErrorPage(request.getSession(), response, this, e);
			}
		}
		else
		{
			System.out.println("filter Bot passed");
			filterChain.doFilter(request, response);
		}
	}
	
	private String getIP(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");
		System.out.println("x-formwarded-for: "+ip);
		System.out.println("getRemoteAdr    : "+request.getRemoteAddr());
		if (ip == null || ip.isBlank())
			ip = request.getRemoteAddr();
		return ip;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
