package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.WebUnameException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterAccountExistence extends OncePerRequestFilter
{
	private final String ERR1 = "Filter Account Existence failed";
	
	private SessionManager sm;
	private DaoLoginInfo dao;
	
	
	
	public FilterAccountExistence(SessionManager sm, DaoLoginInfo dao)
	{
		this.sm = sm;
		this.dao = dao;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 3, in Filter AccExist. (all pages)");
		
		try
		{
			boolean filterFailed;
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				ModelLoginInfo model = new ModelLoginInfo();
				model.setUsername(sm.getSessionLogin().getUsername());
				
				filterFailed = sm.getSessionLogin().isLoggedIn() && !dao.checkUsernameCaseSen(model);
				
				if (filterFailed)
					sm.clearLoginState();
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
