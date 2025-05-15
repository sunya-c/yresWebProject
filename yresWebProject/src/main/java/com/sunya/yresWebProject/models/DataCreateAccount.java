package com.sunya.yresWebProject.models;

import org.springframework.web.util.HtmlUtils;

/**
 * A class created for viewing purpose. Use this class to display dynamic data
 * on <strong>CreateAccount page</strong>.
 */
public class DataCreateAccount
{
	private String usernamePreTyped = null;
	private String usernameErr = null;
	private String password1Err = null;
	private String password2Err = null;


	public String getUsernamePreTyped()
	{
		return usernamePreTyped;
	}


	public void setUsernamePreTyped(String usernamePreTyped)
	{
		this.usernamePreTyped = (usernamePreTyped==null)? null : HtmlUtils.htmlEscape(usernamePreTyped);
	}


	public String getUsernameErr()
	{
		return usernameErr;
	}


	public void setUsernameErr(String usernameErr)
	{
		this.usernameErr = (usernameErr==null)? null : HtmlUtils.htmlEscape(usernameErr);
	}


	public String getPassword1Err()
	{
		return password1Err;
	}


	public void setPassword1Err(String password1Err)
	{
		this.password1Err = (password1Err==null)? null : HtmlUtils.htmlEscape(password1Err);
	}


	public String getPassword2Err()
	{
		return password2Err;
	}


	public void setPassword2Err(String password2Err)
	{
		this.password2Err = (password2Err==null)? null : HtmlUtils.htmlEscape(password2Err);
	}
}
