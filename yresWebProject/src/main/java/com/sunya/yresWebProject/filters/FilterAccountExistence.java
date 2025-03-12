package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.WebUnameException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FilterAccountExistence extends OncePerRequestFilter
{
	final String ERR1 = "Filter Account Existence failed";
	String errText = "";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		System.out.println("Order: 2, in Filter AccExist. (all pages)");
		HttpSession session = request.getSession();
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);
		DaoLoginInfo dao = YresWebProjectApplication.context.getBean(DaoLoginInfo.class);
		
		ModelLoginInfo modelLogin = new ModelLoginInfo();
		modelLogin.setUsername((String)session.getAttribute(sm.LOGIN_USERNAME));
		
		try
		{
			if ((session.getAttribute(sm.LOGIN_LOGGED_IN) != null) && ((boolean)session.getAttribute(sm.LOGIN_LOGGED_IN) == true)
					&& !dao.checkUsernameCaseSen(modelLogin))
			{
				sm.removeLoginState();
				errText = ERR1;
				PrintError.println(errText);
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
			PrintError.toErrorPage(session, response, this, e);
		}
	}

	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
