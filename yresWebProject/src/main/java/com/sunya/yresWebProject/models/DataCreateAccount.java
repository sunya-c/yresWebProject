package com.sunya.yresWebProject.models;

public class DataCreateAccount
{
	private String usernamePreTyped = null;
	private String usernameErr = null;
	private String passwordErr1 = null;
	private String passwordErr2 = null;
	
	
	public String getUsernamePreTyped()
	{
		return usernamePreTyped;
	}
	public void setUsernamePreTyped(String usernamePreTyped)
	{
		this.usernamePreTyped = usernamePreTyped;
	}
	public String getUsernameErr()
	{
		return usernameErr;
	}
	public void setUsernameErr(String usernameErr)
	{
		this.usernameErr = usernameErr;
	}
	public String getPasswordErr1()
	{
		return passwordErr1;
	}
	public void setPasswordErr1(String passwordErr1)
	{
		this.passwordErr1 = passwordErr1;
	}
	public String getPasswordErr2()
	{
		return passwordErr2;
	}
	public void setPasswordErr2(String passwordErr2)
	{
		this.passwordErr2 = passwordErr2;
	}
}
