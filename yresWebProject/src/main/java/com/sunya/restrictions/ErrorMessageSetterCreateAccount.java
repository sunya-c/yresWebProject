package com.sunya.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.managers.SessionManager;

import jakarta.servlet.http.HttpSession;

@Component
public class ErrorMessageSetterCreateAccount
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	
	
	
	
	public void setUsernameErr(ErrMsg errMessage)
	{
		session.setAttribute(sm.CREATEACCOUNT_UNAME_ERR, errMessage.toString());
	}
	
	public void setPasswordErr1(ErrMsg errMessage)
	{
		session.setAttribute(sm.CREATEACCOUNT_PASS_ERR1, errMessage.toString());
	}
	
	public void setPasswordErr2(ErrMsg errMessage)
	{
		session.setAttribute(sm.CREATEACCOUNT_PASS_ERR2, errMessage.toString());
	}
	
}
