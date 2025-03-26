package com.sunya.yresWebProject.filters;

import java.io.IOException;
import java.time.Duration;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
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
	private DaoIPBlacklist dao;
	
	
	
	public FilterBot(DaoIPBlacklist dao)
	{
		this.dao = dao;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 1, in Filter Bot (Home)");
		
		String ip = getIP(request);
		
		String errText = "<br>"+ip+"<br>Your request is suspected to be inhuman.<br>If you're a human, "
									+ "please send your intention to visit our website via \'Give feedback / bug report\' button down below.";
		
		try
		{
			boolean isBlacklisted = dao.isBlacklisted(ip);
			
			if (isBlacklisted)   // If it's already in the blacklist.
			{
				dao.incrementCounter(ip);
				throw new SuspiciousRequestException(errText);
			}
			else   // If NOT in the blacklist.
			{
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
					throw new SomethingWentWrongException("filterbot-01: Limit reached.");
				}
				
				String countryCode = lookUp.getCountryCode();   // Look for the country.
				System.out.println("ip: "+ip);
				System.out.println("code: "+countryCode);
				
				if (countryCode == null || !countryCode.equals("TH"))   // If the country is NOT Thailand (assume it's a bot).
				{
					dao.addToBlacklist(ip, countryCode);   // Add the IP to blacklist
					
					throw new SuspiciousRequestException(errText);
				}
				else   // If from Thailand.
				{
					System.out.println("filter Bot passed");
					filterChain.doFilter(request, response);
				}
			}
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(response, e);
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
