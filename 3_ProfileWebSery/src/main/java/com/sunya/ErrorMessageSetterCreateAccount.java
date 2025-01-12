package com.sunya;

import jakarta.servlet.http.HttpSession;

public class ErrorMessageSetterCreateAccount
{
	HttpSession session;
	
	final String USERNAME_ERR = "usernameErr";
	final String PASSWORD_ERR1 = "passwordErr1";
	final String PASSWORD_ERR2 = "passwordErr2";
	
	// Constructor
	public ErrorMessageSetterCreateAccount(HttpSession session)
	{
		this.session = session;
	}
	// end -- Constructor

	
	public void setUsernameErr(ErrMsg errMessage)
	{
		session.setAttribute(USERNAME_ERR, errMessage.toString());
	}
	
	public void setPasswordErr1(ErrMsg errMessage)
	{
		session.setAttribute(PASSWORD_ERR1, errMessage.toString());
	}
	
	public void setPasswordErr2(ErrMsg errMessage)
	{
		session.setAttribute(PASSWORD_ERR2, errMessage.toString());
	}
	
}
