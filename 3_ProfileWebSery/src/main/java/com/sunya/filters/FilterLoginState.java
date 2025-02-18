package com.sunya.filters;

import java.io.IOException;

import com.sunya.PrintError;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class FilterLoginState extends HttpFilter implements Filter
{
	final String ERR1 = "Filter LoginState failed";
	String errText = "";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		System.out.println("Order : 3, in Filter Login State : Welcome");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		if (
				(session.getAttribute("loggedIn") != null) &&
				((boolean) session.getAttribute("loggedIn") == true)
				)
		{
			System.out.println("Filter LoginState passed");
			chain.doFilter(req, res);
		}
		else
		{
			errText = ERR1;
			PrintError.println(errText);
			res.sendRedirect("LoginPage.jsp");
		}

	}
}
