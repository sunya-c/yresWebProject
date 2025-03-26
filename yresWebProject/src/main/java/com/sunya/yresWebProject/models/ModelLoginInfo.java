package com.sunya.yresWebProject.models;

/**
 * This class is a model for database. This class can be use when interacting
 * with <strong>logininfo</strong> table.
 */
public class ModelLoginInfo
{
	private String username;
	private String password;
	private String tempaccount;
	private String timecreated;
	
	
	
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
	public String getTempaccount()
	{
		return tempaccount;
	}
	public void setTempaccount(String tempaccount)
	{
		this.tempaccount = tempaccount;
	}
	public String getTimecreated()
	{
		return timecreated;
	}
	public void setTimecreated(String timecreated)
	{
		this.timecreated = timecreated;
	}
}
