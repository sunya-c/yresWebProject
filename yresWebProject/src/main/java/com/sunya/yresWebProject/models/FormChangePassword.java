package com.sunya.yresWebProject.models;

public class FormChangePassword implements FormPassword
{
	private String currentPassword;
	private String password1;
	private String password2;
	
	public String getCurrentPassword()
	{
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword)
	{
		this.currentPassword = currentPassword;
	}
	public String getPassword1()
	{
		return password1;
	}
	public void setPassword1(String newPassword1)
	{
		this.password1 = newPassword1;
	}
	public String getPassword2()
	{
		return password2;
	}
	public void setPassword2(String newPassword2)
	{
		this.password2 = newPassword2;
	}
}
