package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;
import java.time.Duration;

import org.springframework.dao.DataAccessException;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.daos.DaoSiteUsage;
import com.sunya.yresWebProject.daos.PageUsageinfo;
import com.sunya.yresWebProject.managers.CookieManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterSiteUsage
{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PageUsageinfo whichPage;
	private DaoSiteUsage dao;
	private String refNumber;
	private String ip;



	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
								PageUsageinfo whichPage, Object obj) throws ServletException, IOException
	{
		this.request = request;
		this.response = response;
		this.whichPage = whichPage;
		this.ip = request.getRemoteAddr();
		
		dao = YresWebProjectApplication.context.getBean(DaoSiteUsage.class);
		
		CookieManager cm = new CookieManager(request.getCookies());
		refNumber = cm.getCookieValue(cm.CLIENT_REF);
		
		try
		{
			if (refNumber!=null && dao.isExistingRefNumber(refNumber))
			{
				String usageIP = dao.getIP(refNumber);
				
				if (ip!=null && usageIP!=null && !ip.equals(usageIP))
					refNumber = null; // refNumber = null to create new refNumber
			}
			else
				refNumber = null;
			
			String resultRefNumber = dao.increaseCounter(refNumber, whichPage);
			
			checkOutcome(resultRefNumber, cm, filterChain);
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(request.getSession(), response, obj, e);
		}
	}



	/**
	 * For HomePage (filterNumber 3) and ErrorPage (filterNumber 1), add IP address
	 * to usageinfo table
	 * 
	 * @param resultRefNumber
	 * @param cm
	 * @param filterChain
	 * @param ip
	 * @throws IOException
	 * @throws ServletException
	 */
	private void checkOutcome(String resultRefNumber, CookieManager cm, FilterChain filterChain) throws IOException, ServletException
	{
		if (whichPage.getColumnOrder()==1 || whichPage.getColumnOrder()==3)
		{
			if (ip!=null)
			{
				if (dao.getIP(resultRefNumber)==null)
					dao.addIP(ip, resultRefNumber);
			}
		}

		setupRefInCookie(cm, resultRefNumber);
		filterChain.doFilter(request, response);
	}



	private void setupRefInCookie(CookieManager cm, String resultRefNumber)
	{
		Cookie cookie = new Cookie(cm.CLIENT_REF, resultRefNumber);
		cookie.setMaxAge((int)Duration.ofDays(7).getSeconds());
		response.addCookie(cookie);
	}
}
