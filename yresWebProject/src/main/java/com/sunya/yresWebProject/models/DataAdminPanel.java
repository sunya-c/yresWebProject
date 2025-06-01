package com.sunya.yresWebProject.models;

import java.util.ArrayList;

import org.springframework.web.util.HtmlUtils;

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
		this.actionResults = (actionResults==null)? null : HtmlUtils.htmlEscape(actionResults);
	}
	public String getUploadResumeErr()
	{
		return uploadResumeErr;
	}
	public void setUploadResumeErr(String uploadResumeErr)
	{
		this.uploadResumeErr = (uploadResumeErr==null)? null : HtmlUtils.htmlEscape(uploadResumeErr);
	}
	public String getResumeVersionErr()
	{
		return resumeVersionErr;
	}
	public void setResumeVersionErr(String resumeVersionErr)
	{
		this.resumeVersionErr = (resumeVersionErr==null)? null : HtmlUtils.htmlEscape(resumeVersionErr);
	}
	public String getAnnouncementErr()
	{
		return announcementErr;
	}
	public void setAnnouncementErr(String announcementErr)
	{
		this.announcementErr = (announcementErr==null)? null : HtmlUtils.htmlEscape(announcementErr);
	}
	public ArrayList<ModelDownloadinfo> getResumeModels()
	{
		return resumeModels;
	}
	public void setResumeModels(ArrayList<ModelDownloadinfo> resumeModels)
	{
		resumeModels.stream().forEach(model -> model.setFilename(HtmlUtils.htmlEscape(model.getFilename())));
		this.resumeModels = resumeModels;
	}
}
