package com.sunya.yresWebProject.models;

import java.util.ArrayList;

public class DataAdminPanel
{
	private String actionResults;
	private String uploadResumeErr;
	private String resumeVersionErr;
	private String announcementErr;
	private ArrayList<ModelDownloadinfo> resumeModels;
	
	public String getActionResults()
	{
		return actionResults;
	}
	public void setActionResults(String actionResults)
	{
		this.actionResults = actionResults;
	}
	public String getUploadResumeErr()
	{
		return uploadResumeErr;
	}
	public void setUploadResumeErr(String uploadResumeErr)
	{
		this.uploadResumeErr = uploadResumeErr;
	}
	public String getResumeVersionErr()
	{
		return resumeVersionErr;
	}
	public void setResumeVersionErr(String resumeVersionErr)
	{
		this.resumeVersionErr = resumeVersionErr;
	}
	public String getAnnouncementErr()
	{
		return announcementErr;
	}
	public void setAnnouncementErr(String announcementErr)
	{
		this.announcementErr = announcementErr;
	}
	public ArrayList<ModelDownloadinfo> getResumeModels()
	{
		return resumeModels;
	}
	public void setResumeModels(ArrayList<ModelDownloadinfo> resumeModels)
	{
		this.resumeModels = resumeModels;
	}
}
