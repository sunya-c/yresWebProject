package com.sunya.yresWebProject.models;

/**
 * A class created for mapping values come with the request object. Use this
 * class to map <strong>Login</strong>-form values.
 */
public class FormLogin
{
	private String username;
	private String password;


	public String getUsername()
	{
		return username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}
}
