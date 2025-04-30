package com.sunya.yresWebProject.models;

import org.springframework.web.util.HtmlUtils;

/**
 * A class created for viewing purpose. Use this class to display dynamic data
 * on <strong>Feedback page</strong>.
 */
public class DataFeedback
{
	private String titleErr = null;
	private String detailErr = null;
	private String errorMessageErr = null;
	private String titlePreTyped = null;
	private String detailPreTyped = null;
	private String errorMessagePreTyped = null;
	private boolean submittedFeedback = false;
	private String refNumber = null;


	public String getTitleErr()
	{
		return titleErr;
	}


	public void setTitleErr(String titleErr)
	{
		this.titleErr = (titleErr==null)? null : HtmlUtils.htmlEscape(titleErr);
	}


	public String getDetailErr()
	{
		return detailErr;
	}


	public void setDetailErr(String detailErr)
	{
		this.detailErr = (detailErr==null)? null : HtmlUtils.htmlEscape(detailErr);
	}


	public String getErrorMessageErr()
	{
		return errorMessageErr;
	}


	public void setErrorMessageErr(String errorMessageErr)
	{
		this.errorMessageErr = (errorMessageErr==null)? null : HtmlUtils.htmlEscape(errorMessageErr);
	}


	public String getTitlePreTyped()
	{
		return titlePreTyped;
	}


	public void setTitlePreTyped(String titlePreTyped)
	{
		this.titlePreTyped = (titlePreTyped==null)? null : HtmlUtils.htmlEscape(titlePreTyped);
	}


	public String getDetailPreTyped()
	{
		return detailPreTyped;
	}


	public void setDetailPreTyped(String detailPreTyped)
	{
		this.detailPreTyped = (detailPreTyped==null)? null : HtmlUtils.htmlEscape(detailPreTyped);
	}


	public String getErrorMessagePreTyped()
	{
		return errorMessagePreTyped;
	}


	public void setErrorMessagePreTyped(String errorMessagePreTyped)
	{
		this.errorMessagePreTyped = (errorMessagePreTyped==null)? null : HtmlUtils.htmlEscape(errorMessagePreTyped);
	}


	public boolean isSubmittedFeedback()
	{
		return submittedFeedback;
	}


	public void setSubmittedFeedback(boolean submittedFeedback)
	{
		this.submittedFeedback = submittedFeedback;
	}


	public String getRefNumber()
	{
		return refNumber;
	}


	public void setRefNumber(String refNumber)
	{
		this.refNumber = (refNumber==null)? null : HtmlUtils.htmlEscape(refNumber);
	}

}
