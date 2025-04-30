package com.sunya.yresWebProject.rest.repositories.models;

public class ModelIPBlacklist
{
	private String ipAddress;
	private String countryCode;
	private int blockCount;
	
	
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
	public int getBlockCount()
	{
		return blockCount;
	}
	public void setBlockCount(int blockCount)
	{
		this.blockCount = blockCount;
	}
	
	@Override
	public String toString()
	{
		return "ModelIPBlacklist [ipAddress="+ipAddress+", countryCode="+countryCode+", blockCount="+blockCount+"]";
	}
	
	
}
