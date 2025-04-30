package com.sunya.yresWebProject.models;

import org.springframework.web.util.HtmlUtils;

public class DataRestApi
{
	private String domainName;
	
	
	public String getDomainName()
	{
		return domainName;
	}

	public void setDomainName(String domainName)
	{
		this.domainName = HtmlUtils.htmlEscape("https://"+domainName);
	}
}
