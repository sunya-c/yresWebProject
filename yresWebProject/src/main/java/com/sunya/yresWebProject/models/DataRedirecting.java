package com.sunya.yresWebProject.models;

/**
 * A class created for viewing purpose. Use this class to display dynamic data
 * on <strong>Redirecting page</strong>.
 */
public class DataRedirecting
{
	private String message;
	private String destinationPage;
	private String destinationUrl;
	
	
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getDestinationPage()
	{
		return destinationPage;
	}
	public void setDestinationPage(String destinationPage)
	{
		this.destinationPage = destinationPage;
	}
	public String getDestinationUrl()
	{
		return destinationUrl;
	}
	public void setDestinationUrl(String destinationUrl)
	{
		this.destinationUrl = destinationUrl;
	}
}
