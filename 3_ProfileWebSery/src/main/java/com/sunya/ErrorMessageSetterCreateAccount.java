package com.sunya;

import jakarta.servlet.http.HttpSession;

public class ErrorMessageSetterCreateAccount
{
	private HttpSession session;
	private SessionManager sm;
	
	
	// Constructor
	public ErrorMessageSetterCreateAccount(HttpSession session)
	{
		this.session = session;
		sm = new SessionManager(session);
	}
	// end -- Constructor

	
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
