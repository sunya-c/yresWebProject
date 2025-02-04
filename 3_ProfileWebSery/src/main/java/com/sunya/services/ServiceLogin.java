package com.sunya.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.daos.DaoLoginInfo;
import com.sunya.managers.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class ServiceLogin
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	
	private String fromPage;

	public String sLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		fromPage = (String) session.getAttribute(sm.LOGIN_FROMPAGE);
		if (fromPage == null)
		{
			fromPage = "Home";
		}

		sm.removeLoginErr();
		session.setAttribute(sm.LOGIN_UNAME_PRETYPED, username);


		if (!dao.checkUsernameCaseSen(username)) // if username is invalid
		{
			session.setAttribute(sm.LOGIN_UNAME_ERR, "Invalid username!!!");

			return fromPage;
		}
		else // if username is valid, proceed with password check
		{
			if (!dao.checkPasswordCaseSen(username, password))
			{
				session.setAttribute(sm.LOGIN_PASS_ERR, "Incorrect password!!!");

				return fromPage;
			}
			else
			{
				session.removeAttribute(sm.LOGIN_UNAME_PRETYPED);
				session.setAttribute(sm.LOGIN_USERNAME, username);
				session.setAttribute(sm.LOGIN_LOGGED_IN, true);
				
				return fromPage;
			}
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}
