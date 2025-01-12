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
		
		fromPage = (String) session.getAttribute("fromPage");
		if (fromPage == null)
		{
			fromPage = "WelcomePage.jsp";
		}
		System.out.println(fromPage);

		DaoLoginInfo dao = new DaoLoginInfo();

		session.invalidate();
		session = request.getSession();
		session.setAttribute("preTypeUsername", username);

		try
		{
			if (!dao.checkUsernameCaseSen(username)) // if username is invalid
			{
				session.setAttribute("wrongUsername", "Invalid username!!!");

				response.sendRedirect(fromPage);
			}
			else // if username is valid, proceed with password check
			{
				if (!dao.checkPasswordCaseSen(username, password))
				{
					session.setAttribute("wrongPassword", "Incorrect password!!!");

					response.sendRedirect(fromPage);
				}
				else
				{
					session.setAttribute("username", username);
					session.setAttribute("loggedIn", true);
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
