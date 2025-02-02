package com.sunya.filters;

import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Duration;

import org.springframework.core.annotation.Order;

import com.sunya.PrintError;
import com.sunya.daos.DaoIPBlacklist;
import com.sunya.exceptions.SuspiciousRequestException;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.cache.SimpleCache;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

//@WebFilter("/PreHomePage.jsp")
//@Priority(0)
public class FilterBot extends HttpFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String ip = req.getRemoteAddr();
		
		IPinfo info = new IPinfo.Builder().setCache(new SimpleCache(Duration.ofDays(7))).build();
		IPResponse lookUp = null;
		
		try
		{
			lookUp = info.lookupIP(ip);
		}
		catch (RateLimitedException e)
		{
		}
		
		String countryCode = lookUp.getCountryCode();

		if (countryCode == null || !countryCode.equals("TH"))
		{
			req.setAttribute("filterBot", "failed");
			
			DaoIPBlacklist dao = new DaoIPBlacklist();
			
			if (dao.isBlacklited(ip))
				dao.increaseCount(ip);
			else
				dao.addToBlacklist(ip, countryCode);
			
			try
			{
				throw new SuspiciousRequestException("<br>"+ip+"<br>Your request is suspected to be inhuman.<br>If you're a human, "
						+ "please send your intention to visit our website via \"Give feedback / bug report\" button down below.");
			}
			catch (SuspiciousRequestException e)
			{
				PrintError.toErrorPage(req.getSession(), res, this, e);
			}
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
}
