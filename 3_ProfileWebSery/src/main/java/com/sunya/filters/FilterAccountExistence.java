package com.sunya.filters;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.PrintError;
import com.sunya.daos.DaoLoginInfo;
import com.sunya.exceptions.WebUnameException;
import com.sunya.managers.SessionManager;

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
		SessionManager sm = new SessionManager(session);

		try
		{
			DaoLoginInfo dao = new DaoLoginInfo();

			if ((session.getAttribute(sm.LOGIN_LOGGED_IN) != null) && ((boolean)session.getAttribute(sm.LOGIN_LOGGED_IN) == true)
					&& !dao.checkUsername((String) session.getAttribute(sm.LOGIN_USERNAME)))
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
		catch (SQLException | ServletException | WebUnameException e)
		{
			PrintError.toErrorPage(session, response, this, e);
		}
	}

}
