package com.sunya.yresWebProject.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.models.ModelLoginInfo;
import com.sunya.yresWebProject.restrictions.ErrMsg;
import com.sunya.yresWebProject.restrictions.ErrorMessageSetterCreateAccount;
import com.sunya.yresWebProject.restrictions.RestrictionsCreateAccount;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

@Service
public class ServiceCreateAccount
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	
	
	
	public String sCreateAccount(FormCreateAccount formCA) throws SQLException, ServletException
	{
		sm.removeCreateAccountErr();
		session.setAttribute(sm.CREATEACCOUNT_UNAME_PRETYPED, formCA.getUsername());
		
		RestrictionsCreateAccount restriction = YresWebProjectApplication.context
									.getBean(RestrictionsCreateAccount.class);
		
		if (restriction.checkRestriction(formCA))
		{
			ModelLoginInfo model = new ModelLoginInfo();
			model.setUsername(formCA.getUsername());
			model.setPassword(formCA.getPassword1());
			
			try
			{
				dao.addUser(model);
			}
			catch (SomethingWentWrongException e)
			{
				ErrorMessageSetterCreateAccount errSetter = YresWebProjectApplication.context
											.getBean(ErrorMessageSetterCreateAccount.class);
				ErrMsg errMessage = ErrMsg.CREATEACCOUNT_UNAME_LENGTH;
				errMessage.setCustomErrMessage("Something's wrong, pls try again.");
				errSetter.setUsernameErr(errMessage);
				session.setAttribute(sm.FROM_SERVLET, toString());
				return "createAccount";
			}
			
			session.setAttribute(sm.REDIRECT_MESSAGE, "Done!");
			session.setAttribute(sm.REDIRECT_DESTINATION, "Home page");
			session.setAttribute(sm.FROM_SERVLET, toString());
			session.setAttribute(sm.LOGIN_UNAME_PRETYPED, formCA.getUsername());
			
			return "redirecting";
		}
		else
		{
			session.setAttribute(sm.FROM_SERVLET, toString());
			return "createAccount";
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
