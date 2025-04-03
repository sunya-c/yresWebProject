package com.sunya.yresWebProject.filters.siteUsage;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoSiteUsage;
import com.sunya.yresWebProject.daos.PageUsageinfo;
import com.sunya.yresWebProject.managers.CookieManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterSiteUsage
{
	@Autowired
	private CookieManager cm;
	@Autowired
	private DaoSiteUsage dao;


	/**
	 * The logic operation for filter site usage. Since all FilterSiteUsage share a
	 * common logic operation, their common part are combined into one, which is
	 * this method.<br>
	 * <br>
	 * What this method does:<br>
	 * <br>
	 * 1. Retrieves refNumber from the client's cookie, if not available, create new
	 * refNumber. If refNumber from the client's cookie is available but does NOT
	 * exist in the database, create new refNumber.<br>
	 * <br>
	 * 2. If refNumber is valid, AND the client's IP address AND IP address in the
	 * database are NOT null, it checks if the client's IP address matches IP
	 * address in the database, if NOT, create new refNumber. This is done to
	 * prevent stolen cookie (hacked) from being used in a different IP address.<br>
	 * <br>
	 * 3. Increments the number of usage in the database of the page specified in
	 * {@code whichPage}.<br>
	 * <br>
	 * 4. Prepares cookie and adds it to the response object.<br>
	 * <br>
	 * 5. doFilter().
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @param whichPage
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
								PageUsageinfo whichPage) throws ServletException, IOException
	{
		String ip = getIP(request);

		String refNumber = cm.getCookieValue(request.getCookies(), CookieManager.CLIENT_REF);

		try
		{
			if (refNumber!=null)
			{
				if (!dao.doesExistRefNumber(refNumber))
					refNumber = null;
				else
				{
					String usageIP = dao.getIP(refNumber);

					if (ip!=null && usageIP!=null && !ip.equals(usageIP))
						refNumber = null;
				}
			}

			// refNumber == null to create new refNumber
			String resultRefNumber = dao.incrementCounter(refNumber, whichPage);

			addIPToUsageinfo(resultRefNumber, ip, whichPage);

			setupRefInCookie(resultRefNumber, response);
			filterChain.doFilter(request, response);
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(response, e);
		}
	}


	/**
	 * For HomePage (filterNumber 3) and ErrorPage (filterNumber 1), add IP address
	 * to usageinfo table.
	 * 
	 * @param resultRefNumber
	 * @param ip
	 * @param whichPage
	 */
	private void addIPToUsageinfo(String resultRefNumber, String ip, PageUsageinfo whichPage)
	{
		if (whichPage.getColumnOrder()==1 || whichPage.getColumnOrder()==3)
		{
			if (ip!=null)
			{
				if (dao.getIP(resultRefNumber)==null)
					dao.addIP(ip, resultRefNumber);
			}
		}
	}


	private void setupRefInCookie(String resultRefNumber, HttpServletResponse response)
	{
		Cookie cookie = new Cookie(CookieManager.CLIENT_REF, resultRefNumber);
		cookie.setMaxAge((int)Duration.ofDays(7).getSeconds());
		response.addCookie(cookie);
	}


	private String getIP(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");

		if (ip==null || ip.isBlank())
			ip = request.getRemoteAddr();
		return ip;
	}
}
