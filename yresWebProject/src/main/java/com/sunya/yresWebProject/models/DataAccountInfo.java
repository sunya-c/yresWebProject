package com.sunya.yresWebProject.models;

public class DataAccountInfo
{
	private String currentPasswordErr;
	private String password1Err;
	private String password2Err;
	
	public String getCurrentPasswordErr()
	{
		return currentPasswordErr;
	}
	public void setCurrentPasswordErr(String currentPasswordErr)
	{
		this.currentPasswordErr = currentPasswordErr;
	}
	public String getPassword1Err()
	{
		return password1Err;
	}
	public void setPassword1Err(String password1Err)
	{
		this.password1Err = password1Err;
	}
	public String getPassword2Err()
	{
		return password2Err;
	}
	public void setPassword2Err(String password2Err)
	{
		this.password2Err = password2Err;
	}
}
