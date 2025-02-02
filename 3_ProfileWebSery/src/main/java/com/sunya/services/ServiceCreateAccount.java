package com.sunya.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sunya.ErrMsg;
import com.sunya.ErrorMessageSetterCreateAccount;
import com.sunya.PrintError;
import com.sunya.RestrictionsCreateAccount;
import com.sunya.daos.DaoLoginInfo;
import com.sunya.managers.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class ServiceCreateAccount
{
	public String sCreateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		sm.removeCreateAccountErr();
		session.setAttribute(sm.CREATEACCOUNT_UNAME_PRETYPED, username);

		DaoLoginInfo dao = new DaoLoginInfo();
		
		ErrorMessageSetterCreateAccount errSetter = new ErrorMessageSetterCreateAccount(session); //TODO: try to put this into the restriction class so that this line can be removed.
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
					session.setAttribute(sm.REDIRECT_MESSAGE, "Done!");
					session.setAttribute(sm.REDIRECT_DESTINATION, "Home page");
					session.setAttribute("fromServlet", toString());
					session.setAttribute(sm.LOGIN_UNAME_PRETYPED, username);
					
					return "redirecting";
				}
				else
				{
					ErrMsg CUSTOM_ERR = ErrMsg.CREATEACCOUNT_UNAME_DUPLICATE;
					CUSTOM_ERR.setCustomErrMessage("Something's wrong, please try again.");
					errSetter.setUsernameErr(CUSTOM_ERR);
					session.setAttribute("fromServlet", toString());
					return "createAccount";
				}
			}
			else
				session.setAttribute("fromServlet", toString());
				return "createAccount";
		}
		catch (ServletException e)
		{
			return PrintError.toErrorPage(session, this, e);
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
