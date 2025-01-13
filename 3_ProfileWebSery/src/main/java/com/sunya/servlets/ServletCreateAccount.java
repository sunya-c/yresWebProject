package com.sunya.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.sunya.ErrMsg;
import com.sunya.ErrorMessageSetterCreateAccount;
import com.sunya.PrintError;
import com.sunya.RestrictionsCreateAccount;
import com.sunya.daos.DaoLoginInfo;

@WebServlet("/ServletCreateAccount")
public class ServletCreateAccount extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession();
		session.setAttribute("preTypedCreateUsername", username);

		DaoLoginInfo dao = new DaoLoginInfo();
		
		ErrorMessageSetterCreateAccount errSetter = new ErrorMessageSetterCreateAccount(session);
		RestrictionsCreateAccount restriction = new RestrictionsCreateAccount(
				dao,
				errSetter,
				username,
				password1,
				password2);
		
		try
		{
			if (restriction.checkRestriction())
			{
				if (dao.addUser(username, password1))
				{
					session.setAttribute("message", "Done!");
					session.setAttribute("destinationPage", "\"Home page\"");
					session.setAttribute("fromServlet", getServletName());
					session.setAttribute("preTypeUsername", username);
					RequestDispatcher rd = request.getRequestDispatcher("RedirectingPage.jsp");
					rd.forward(request, response);
				}
				else
				{
					ErrMsg CUSTOM_ERR = ErrMsg.DUPLICATE_UNAME_ERR;
					CUSTOM_ERR.setCustomErrMessage("Something's wrong, please try again.");
					errSetter.setUsernameErr(CUSTOM_ERR);
					response.sendRedirect("CreateAccountPage.jsp");
				}
			}
			else
				response.sendRedirect("CreateAccountPage.jsp");
		}
		catch (ServletException e)
		{
			PrintError.toErrorPage(session, response, this, e);
		}

		session.setAttribute("fromServlet", this.getServletName());
	}
}
