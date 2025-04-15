package com.sunya.yresWebProject.models;

public class DataRestApi
{
	private String domainName;
	
	
	public String getDomainName()
	{
		return domainName;
	}

	public void setDomainName(String domainName)
	{
		this.domainName = "http://"+domainName;
	}
}
