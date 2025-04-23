package com.sunya.yresWebProject.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.SuspiciousRequestException;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterBot extends OncePerRequestFilter
{
	private DaoIPBlacklist dao;
	private Environment env;
	private IPinfo info;

	private List<String> allowedUrl = List.of("/error", "/yresError", "/feedback", "/resources/css",
								"/resources/outBox", "/resources/pics", "/restApi/sSendResponse");


	public FilterBot(DaoIPBlacklist dao, Environment env, IPinfo info)
	{
		this.dao = dao;
		this.env = env;
		this.info = info;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order: 0, in Filter Bot (/*)");

		String requestedUrl = request.getRequestURL().toString();
		System.out.println(requestedUrl);

		String requestedUri = request.getRequestURI();

		if (requestedUri.equals("/") || allowedUrl.stream().anyMatch(requestedUri::startsWith) || requestedUri.equals("/healthcheck"))
		{
			System.out.println("filter Bot skipped - Allowed URI");
			filterChain.doFilter(request, response);
			return;
		}

		String ip = getIP(request);

		String errText = "<br>"+ip+"<br>Your request is suspected to be inhuman.<br>If you're a human, "
									+"please send your intention to visit our website via \'Give feedback / bug report\' button down below.";

		try
		{
			boolean isBlacklisted = dao.isBlacklisted(ip);

//			if (!isBlacklisted && requestedUrl.contains(env.getProperty("yres.domain")))
//			{
//				System.out.println("filter Bot skipped - expected domain name");
//				filterChain.doFilter(request, response);
//				return;
//			}

			if (isBlacklisted) // If it's already in the blacklist.
			{
				dao.incrementCounter(ip);
				throw new SuspiciousRequestException(errText);
			}
			else // If NOT in the blacklist.
			{
				IPResponse lookUp = null;

				try
				{
					lookUp = info.lookupIP(ip);
				}
				catch (Exception e)
				{
					// TODO: Send a counter iteration to the database.
					throw new SomethingWentWrongException("filterbot-01: Limit reached.");
				}

				String countryCode = lookUp.getCountryCode(); // Look for the country.
				System.out.println("IP lookup: "+countryCode+", "+"IP: "+ip);

				if (!"TH".equals(countryCode)) // If the country is NOT Thailand (assume it's a bot).
				{
					dao.addToBlacklist(ip, countryCode); // Add the IP to blacklist

					throw new SuspiciousRequestException(errText);
				}
				else // If from Thailand.
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
		System.out.println("x-formwarded-for        : "+ip);
		if (ip!=null)
		{
			ip = ip.split(",")[0].strip(); // the first one is the client's IP
			System.out.println("x-formwarded-for(client): "+ip);
		}
		System.out.println("getRemoteAdr            : "+request.getRemoteAddr());
		if (ip==null || ip.isBlank())
			ip = request.getRemoteAddr();
		return ip;
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
