package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.WebUnameException;
import com.sunya.yresWebProject.managers.CookieManager;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FilterAccountExistence extends OncePerRequestFilter
{
	private final String ERR1 = "Filter Account Existence failed";
	
	private SessionManager sm;
	private DaoLoginInfo dao;
	private CookieManager cm;
	
	
	
	public FilterAccountExistence(SessionManager sm, DaoLoginInfo dao, CookieManager cm)
	{
		this.sm = sm;
		this.dao = dao;
		this.cm = cm;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 2, in Filter AccExist. (all pages)");
		
		try
		{
			boolean filterFailed;
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				ModelLoginInfo model = new ModelLoginInfo();
				model.setUsername(sm.getAuthContext().getUsername());
				
				filterFailed = sm.getAuthContext().isAuthenticated() && !dao.checkUsernameCaseSen(model);
				
				if (filterFailed)
				{
					SecurityContextHolder.clearContext();
					sm.setSecurityContext(null);
					sm.getSession().invalidate();
					HttpSession newSession = request.getSession(true);
					response.addCookie(cm.createCookie(CookieManager.JSESSION, newSession.getId(), -1));
					response.addCookie(cm.createCookie(CookieManager.JWT_TOKEN, "", 0));
				}
			}
			
			if (filterFailed)
			{
				PrintError.println(ERR1);
				throw new WebUnameException("This account doesn't exist");
			}
			else
			{
				System.out.println("Filter AccountExistence passed");
				filterChain.doFilter(request, response);
			}
		}
		catch (Exception e)
		{
			PrintError.toErrorPage(response, e);
		}
	}
	
	
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
