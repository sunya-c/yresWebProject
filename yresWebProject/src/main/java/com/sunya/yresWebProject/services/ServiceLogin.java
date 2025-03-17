package com.sunya.yresWebProject.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.FormLogin;
import com.sunya.yresWebProject.models.ModelLoginInfo;

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

	public String sLogin(FormLogin formL) throws SQLException
	{
		fromPage = (String) session.getAttribute(sm.LOGIN_FROMPAGE);
		if (fromPage == null)
		{
			fromPage = "Home";
		}

		sm.removeLoginErr();
		session.setAttribute(sm.LOGIN_UNAME_PRETYPED, formL.getUsername());


		ModelLoginInfo model = new ModelLoginInfo();
		model.setUsername(formL.getUsername());
		model.setPassword(formL.getPassword());
		
		if (!dao.checkUsernameCaseSen(model)) // if username is invalid
		{
			session.setAttribute(sm.LOGIN_UNAME_ERR, "Invalid username!!!");

			return fromPage;
		}
		else // if username is valid, proceed with password check
		{
			if (!dao.checkPasswordCaseSen(model))
			{
				session.setAttribute(sm.LOGIN_PASS_ERR, "Incorrect password!!!");

				return fromPage;
			}
			else
			{
				session.removeAttribute(sm.LOGIN_UNAME_PRETYPED);
				session.setAttribute(sm.LOGIN_USERNAME, formL.getUsername());
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
