package com.sunya.yresWebProject.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.restrictions.ErrMsg;
import com.sunya.yresWebProject.restrictions.ErrorMessageSetterCreateAccount;
import com.sunya.yresWebProject.restrictions.RestrictionsCreateAccount;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class ServiceCreateAccount
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	@Autowired
	private ErrorMessageSetterCreateAccount errSetter;
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private RestrictionsCreateAccount restriction;
	
	public String sCreateAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException
	{
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		sm.removeCreateAccountErr();
		session.setAttribute(sm.CREATEACCOUNT_UNAME_PRETYPED, username);
		
		restriction.setupRestrictionCreateAccount(username, password1, password2);
		
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
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
