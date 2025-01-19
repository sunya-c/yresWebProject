package com.sunya.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.locks.Condition;

import com.mysql.cj.Session;
import com.sunya.daos.DaoLoginInfo;
import com.sunya.managers.SessionManager;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String fromPage;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		
		fromPage = (String) session.getAttribute(sm.LOGIN_FROMPAGE);
		if (fromPage == null)
		{
			fromPage = "WelcomePage.jsp";
		}

		DaoLoginInfo dao = new DaoLoginInfo();

		sm.removeLoginErr();
		session.setAttribute(sm.LOGIN_UNAME_PRETYPED, username);

		try
		{
			if (!dao.checkUsernameCaseSen(username)) // if username is invalid
			{
				session.setAttribute(sm.LOGIN_UNAME_ERR, "Invalid username!!!");

				response.sendRedirect(fromPage);
			}
			else // if username is valid, proceed with password check
			{
				if (!dao.checkPasswordCaseSen(username, password))
				{
					session.setAttribute(sm.LOGIN_PASS_ERR, "Incorrect password!!!");

					response.sendRedirect(fromPage);
				}
				else
				{
					session.removeAttribute(sm.LOGIN_UNAME_PRETYPED);
					session.setAttribute(sm.LOGIN_USERNAME, username);
					session.setAttribute(sm.LOGIN_LOGGED_IN, true);
					response.sendRedirect(fromPage);
				}

			}
		}
		catch (ServletException e)
		{
			session.setAttribute("errorDescription", e);
			session.setAttribute("fromServlet", getServletName());
			response.sendRedirect("ErrorPage.jsp");
		}

	}

}
