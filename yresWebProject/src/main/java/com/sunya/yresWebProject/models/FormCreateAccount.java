package com.sunya.yresWebProject.models;

/**
 * A class created for mapping values come with the request object. Use this
 * class to map <strong>CreateAccount</strong>-form values.
 */
public class FormCreateAccount implements FormPassword
{
	private String username;
	private String password1;
	private String password2;


	public String getUsername()
	{
		return username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}


	public String getPassword1()
	{
		return password1;
	}


	public void setPassword1(String password1)
	{
		this.password1 = password1;
	}


	public String getPassword2()
	{
		return password2;
	}


	public void setPassword2(String password2)
	{
		this.password2 = password2;
	}
}
