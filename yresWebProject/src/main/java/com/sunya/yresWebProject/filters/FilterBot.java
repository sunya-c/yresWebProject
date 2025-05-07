package com.sunya.yresWebProject.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.SuspiciousRequestException;
import com.sunya.yresWebProject.rest.repositories.models.ModelIPBlacklist;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterBot extends OncePerRequestFilter
{
	private DaoIPBlacklist dao;
	private IPinfo info;

	private List<String> allowedUrlPrefix = List.of("/error", "/feedback", "/resources/css", "/resources/outBox",
								"/resources/pics", "/restApi/sSendResponse"); // /yresError removed
	private List<String> allowedUrlExact = List.of("/", "/healthcheck", "/saveBotstodatabase");

	private CustomArrayList cachedBlacklist;
	private CustomArrayList tempBlacklist;


	public FilterBot(DaoIPBlacklist dao, IPinfo info)
	{
		this.dao = dao;
		this.info = info;
		this.cachedBlacklist = new CustomArrayList();
		this.tempBlacklist = new CustomArrayList();
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order: 0, in Filter Bot (/*)");

		String requestedUrl = request.getRequestURL().toString();
		System.out.println(requestedUrl);

		String requestedUri = request.getRequestURI();

		if (allowedUrlExact.stream().anyMatch(requestedUri::equals) || allowedUrlPrefix.stream().anyMatch(requestedUri::startsWith))
		{
			System.out.println("filter Bot skipped - Allowed URI");
			filterChain.doFilter(request, response);
			return;
		}

		String ip = getIP(request);

		String errText = "<br>IPError:"+ip+":IPError<br>Your request is suspected to be inhuman.<br>If you're a human, "
									+"please send your intention to visit our website via \'Give feedback / bug report\' button down below.";

		try
		{
			if (request.getRequestURI().startsWith("/yresError"))
			{
				String errDescrip = request.getParameter("errorDescription");
				int begin;
				int end;
				String checkIp;
				if (ip!=null && errDescrip!=null && (begin=errDescrip.indexOf("IPError:"))!=-1 && (end=errDescrip.indexOf(":IPError"))!=-1)
				{
					checkIp = errDescrip.substring(begin+"IPError:".length(), end);
					System.out.println("checkIp:"+checkIp+".");
					if (!ip.equals(checkIp))
					{
						tempBlacklist.incrementExistingOrAddNew(ip);
					}
				}
				System.out.println("filter Bot skipped - yresError");
				filterChain.doFilter(request, response);
				return;
			}
			if (ip==null)
			{
				if (dao.isBlacklisted("-"))
					dao.incrementCounter("-");
				else
					dao.addToBlacklist("-", "-");
				throw new SuspiciousRequestException(errText);
			}
			if (request.getParameter("bqweqwaisdiqwe")!=null)
			{
				tempBlacklist.incrementExistingOrAddNew(ip);
				System.out.println("BOT!!! bqweqwaisdiqwe");
				throw new SuspiciousRequestException(errText);
			}

			// check whether the IP is blacklisted.
			if (tempBlacklist.incrementExisting(ip)) // Does it exist in tempBlacklist
			{
				System.out.println("filter BOT failed - blacklisted IP tempBlacklist");
				throw new SuspiciousRequestException(errText);
			}
			if (cachedBlacklist.incrementExisting(ip)) // Does it exist in cache
			{
				System.out.println("filter BOT failed - blacklisted IP cache");
				throw new SuspiciousRequestException(errText);
			}
			if (dao.isBlacklisted(ip)) // Does it exist in blacklist database
			{
				cachedBlacklist.incrementExistingOrAddNew(ip);
				System.out.println("filter BOT failed - blacklisted IP");
				throw new SuspiciousRequestException(errText);
			}

			// If the IP is NOT in 'tempBlacklist', 'cachedBlacklist', and 'blacklist database', lookup the IP by 'ipinfo' library.
			IPResponse lookUp = null;
			String countryCode;
			try
			{
				lookUp = info.lookupIP(ip);
				countryCode = lookUp.getCountryCode(); // Look for the country.
				System.out.println("IP lookup: "+countryCode+", "+"IP: "+ip);
			}
			catch (Exception e)
			{
				// TODO: Send a counter iteration to the database.
				throw new SomethingWentWrongException("filterbot-01: Limit reached.");
			}

			// Check whether IP is from 'TH'.
			if ("TH".equals(countryCode))
			{
				System.out.println("filter Bot passed - IP lookup");
				filterChain.doFilter(request, response);
				return;
			}

			// If the country is NOT 'TH', I presume it's a bot request.
			// Add IP to tempBlacklist, or increment if already exists.
			tempBlacklist.incrementExistingOrAddNew(ip, countryCode);
			throw new SuspiciousRequestException(errText);
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

	public CustomArrayList getCachedBlacklist()
	{
		return cachedBlacklist;
	}
	public CustomArrayList getTempBlacklist()
	{
		return tempBlacklist;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
	
	public class CustomArrayList extends ArrayList<ModelIPBlacklist>
	{
		private static final long serialVersionUID = 1797283846835387687L;
		private Object keySynchronized; // for synchronized block
		
		public CustomArrayList()
		{
			keySynchronized = new Object();
		}

		public boolean doesExist(@NotNull String ip)
		{
			synchronized (keySynchronized)
			{
				return stream().anyMatch(model -> ip.equals(model.getIpAddress()));
			}
		}
		public boolean incrementExisting(@NotNull String ip)
		{
			synchronized (keySynchronized)
			{
				return stream().anyMatch(model -> {
					if (ip.equals(model.getIpAddress()))
					{
						model.setBlockCount(model.getBlockCount()+1);
						return true;
					}
					return false;
				});
			}
		}
		public void addNew(@NotNull String ip, @Nullable String countryCode)
		{
			synchronized (keySynchronized)
			{
				ModelIPBlacklist model = new ModelIPBlacklist();
				model.setIpAddress(ip);
				model.setCountryCode(countryCode);
				model.setBlockCount(1);
				add(model);
			}
		}
		public void incrementExistingOrAddNew(@NotNull String ip)
		{
			synchronized (keySynchronized)
			{
				if (!incrementExisting(ip))
				{
					addNew(ip, null);
				}
			}
		}
		public void incrementExistingOrAddNew(@NotNull String ip, @Nullable String countryCode)
		{
			synchronized (keySynchronized)
			{
				if (!incrementExisting(ip))
				{
					addNew(ip, countryCode);
				}
			}
		}
		public Object getKeySynchronized()
		{
			return keySynchronized;
		}
		
	}
}
