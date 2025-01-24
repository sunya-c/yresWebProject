package com.sunya.filters;

import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import com.sunya.PrintError;

@WebFilter("/WelcomePage.jsp")
@Priority(2)
public class FilterLoginState extends HttpFilter implements Filter
{
	final String ERR1 = "Filter LoginState failed";
	String errText = "";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
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
