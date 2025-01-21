package com.sunya.filters.siteUsage;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import com.sunya.PrintError;
import com.sunya.daos.DaoSiteUsage;
import com.sunya.managers.CookieManager;

@WebFilter("/LoginPage.jsp")
@Order(3)
public class FilterSiteUsage3 extends HttpFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		DaoSiteUsage dao = new DaoSiteUsage();
		
		CookieManager cm = new CookieManager(req.getCookies());
		String refNumber = cm.getCookieValue(cm.CLIENT_REF);
		
		int[] updatedUsage;
		
		if (refNumber == null)
			updatedUsage = new int[dao.getArraySize()];
		else
		{
			updatedUsage = dao.getUsage(refNumber);
			if (updatedUsage == null)
			{
				refNumber = null;
				updatedUsage = new int[dao.getArraySize()];
			}
		}
		
		updatedUsage[3] += 1;
		String result = dao.updateUsage(refNumber, updatedUsage);
		
		if (result == null)
		{
			try
			{
				throw new ServletException("SiteUsage update failed.");
			}
			catch (ServletException e)
			{
				PrintError.toErrorPage(req.getSession(), res, this, e);
			}
		}
		else
		{
			Cookie cookie = new Cookie(cm.CLIENT_REF, result);
			cookie.setMaxAge(7*24*60*60);
			res.addCookie(cookie);
			chain.doFilter(req, res);
		}
	}
}
