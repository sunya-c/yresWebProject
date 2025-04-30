package com.sunya.yresWebProject.managers.sessionObjects;

import org.springframework.web.util.HtmlUtils;

/**
 * This class is for storing session-specific values related to
 * <strong>general web data</strong>.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class SessionWeb
{
	private String webNote1 = null;
	private String webVersion = null;
	private String resumeDate = null;


	public String getWebNote1()
	{
		return webNote1;
	}


	public void setWebNote1(String webNote1)
	{
		this.webNote1 = (webNote1==null)? null : HtmlUtils.htmlEscape(webNote1);
	}


	public String getWebVersion()
	{
		return webVersion;
	}


	public void setWebVersion(String webVersion)
	{
		this.webVersion = (webVersion==null)? null : HtmlUtils.htmlEscape(webVersion);
	}


	public String getResumeDate()
	{
		return resumeDate;
	}


	public void setResumeDate(String resumeDate)
	{
		this.resumeDate = (resumeDate==null)? null : HtmlUtils.htmlEscape(resumeDate);
	}
}
