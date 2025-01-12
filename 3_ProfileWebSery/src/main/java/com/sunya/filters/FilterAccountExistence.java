package com.sunya.filters;

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
import com.sunya.daos.DaoLoginInfo;
import com.sunya.exceptions.WebUnameException;

@WebFilter(urlPatterns = {
		"/CreateAccountPage.jsp",
		"/ErrorPage.jsp",
		"/FeedbackPage.jsp",
		"/LoginPage.jsp",
		"/PersonalInformationPage.jsp",
		"/RedirectingPage.jsp",
		"/UnderConstructionPage.jsp",
		"/WelcomePage.jsp",
		"/ServletCreateAccount",
		"/ServletDownloadResume",
		"/ServletFeedback",
		"/ServletLogin",
		"/ServletLogout",
		"/ServletPersonalInformation"})
@Order(1)
public class FilterAccountExistence extends HttpFilter implements Filter
{
	final String ERR1 = "loggedIn attribute is NOT boolean type";
	final String ERR2 = "Filter failed";
	String errText = "";

	private String fromPage;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException
	{
		System.out.println("in Filter Account State.");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		try
		{
			DaoLoginInfo dao = new DaoLoginInfo();

			if ((session.getAttribute("loggedIn") != null) && ((boolean) session.getAttribute("loggedIn") == true)
					&& !dao.checkUsername((String) session.getAttribute("username")))
			{
				session.invalidate();
				session = req.getSession();
				throw new WebUnameException("This account doesn't exist");
			}
		}
		catch (ClassCastException e)
		{
			errText = ERR1;
			PrintError.println(errText);

			// TODO
			// Create an ErrorPage
			// Redirect to the ErrorPage
			// Automatically reset session
			// Redirect back to LoginPage.jsp
		}
		catch (ServletException se)
		{
			session.setAttribute("errorDescription", se);
			session.setAttribute("fromServlet", getFilterName());
			res.sendRedirect("ErrorPage.jsp");
		}
		catch (WebUnameException we)
		{
			session.setAttribute("errorDescription", we);
			session.setAttribute("fromServlet", getFilterName());
			res.sendRedirect("ErrorPage.jsp");
		}

		try
		{
			chain.doFilter(request, response);
		}
		catch (ServletException se)
		{
			session.setAttribute("errorDescription", se);
			session.setAttribute("fromServlet", getFilterName());
			res.sendRedirect("ErrorPage.jsp");
		}

	}
}
