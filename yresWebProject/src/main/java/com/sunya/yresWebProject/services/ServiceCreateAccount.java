package com.sunya.yresWebProject.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataCreateAccount;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.models.ModelLoginInfo;
import com.sunya.yresWebProject.restrictions.ErrMsg;
import com.sunya.yresWebProject.restrictions.RestrictionsCreateAccount;

import jakarta.servlet.ServletException;

@Service
public class ServiceCreateAccount
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private RestrictionsCreateAccount restriction;
	
	
	public String sCreateAccount(FormCreateAccount formCA, DataCreateAccount dataCreateAccount, String codeCreateAccount) throws SQLException, ServletException
	{
//		sm.removeCreateAccountErr();
//		sm.getSessionCreateAccount().setUsernamePreTyped();
		dataCreateAccount.setUsernamePreTyped(formCA.getUsername());
		
		if (restriction.checkRestriction(formCA, dataCreateAccount))
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
//				ErrorMessageSetterCreateAccount errSetter = YresWebProjectApplication.context
//											.getBean(ErrorMessageSetterCreateAccount.class);
				ErrMsg errMessage = ErrMsg.CREATEACCOUNT_UNAME_LENGTH;
				errMessage.setCustomErrMessage("Something's wrong, pls try again.");
//				errSetter.setUsernameErr(errMessage);
				dataCreateAccount.setUsernameErr(errMessage.toString());
//				sm.getSessionFromServlet().setFromServlet(toString());
				return Url.createAccount+"?code="+codeCreateAccount;
			}
			
//			sm.getSessionFromServlet().setFromServlet(toString());
			sm.getSessionLogin().setUsernamePreTyped(formCA.getUsername());
			
			String codeRedirecting = sm.getSessionRedirecting().generateCode();
			
			return Url.redirecting+"?message=Done!&destinationPage=Home page&code="+codeRedirecting;
		}
		else
		{
//			sm.getSessionFromServlet().setFromServlet(toString());
			return Url.createAccount+"?code="+codeCreateAccount;
		}
	}
	
	
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
