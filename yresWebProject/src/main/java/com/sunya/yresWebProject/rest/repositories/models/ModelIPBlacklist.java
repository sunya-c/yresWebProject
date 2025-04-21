package com.sunya.yresWebProject.rest.repositories.models;

public class ModelIPBlacklist
{
	private String ipAddress;
	private String countryCode;
	private String blockCount;
	
	
	public String getIpAddress()
	{
		return ipAddress;
	}
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
	public String getCountryCode()
	{
		return countryCode;
	}
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}
	public String getBlockCount()
	{
		return blockCount;
	}
	public void setBlockCount(String blockCount)
	{
		this.blockCount = blockCount;
	}
}
