package com.sunya.filters;

import java.io.IOException;

import com.sunya.PrintError;
import com.sunya.daos.DaoLoginInfo;
import com.sunya.exceptions.WebUnameException;
import com.sunya.managers.SessionManager;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class FilterAccountExistence extends HttpFilter implements Filter
{
	final String ERR1 = "Filter AccountExistence failed";
	String errText = "";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException
	{
		System.out.println("Order : 2, in Filter Account Existence : ALL pages");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		try
		{
			DaoLoginInfo dao = new DaoLoginInfo();

			if ((session.getAttribute("loggedIn") != null) && ((boolean) session.getAttribute("loggedIn") == true)
					&& !dao.checkUsername((String) session.getAttribute("username")))
			{
				SessionManager sm = new SessionManager(session);
				sm.removeLoginState();
				errText = ERR1;
				PrintError.println(errText);
				throw new WebUnameException("This account doesn't exist");
			}
			else
			{
				System.out.println("Filter AccountExistence passed");
				chain.doFilter(req, res);
			}
		}
		catch (ServletException se)
		{
			PrintError.toErrorPage(session, res, this, se);
		}
		catch (WebUnameException we)
		{
			PrintError.toErrorPage(session, res, this, we);
		}
	}
}
