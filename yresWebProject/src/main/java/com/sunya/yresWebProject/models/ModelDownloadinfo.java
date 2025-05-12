package com.sunya.yresWebProject.models;

import java.io.InputStream;

public class ModelDownloadinfo
{
	String filename = null;
	InputStream filedata = null;
	public String getFilename()
	{
		return filename;
	}
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	public InputStream getFiledata()
	{
		return filedata;
	}
	public void setFiledata(InputStream filedata)
	{
		this.filedata = filedata;
	}
}
