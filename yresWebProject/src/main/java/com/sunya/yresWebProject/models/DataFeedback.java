package com.sunya.yresWebProject.models;

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
		this.titleErr = titleErr;
	}


	public String getDetailErr()
	{
		return detailErr;
	}


	public void setDetailErr(String detailErr)
	{
		this.detailErr = detailErr;
	}


	public String getErrorMessageErr()
	{
		return errorMessageErr;
	}


	public void setErrorMessageErr(String errorMessageErr)
	{
		this.errorMessageErr = errorMessageErr;
	}


	public String getTitlePreTyped()
	{
		return titlePreTyped;
	}


	public void setTitlePreTyped(String titlePreTyped)
	{
		this.titlePreTyped = titlePreTyped;
	}


	public String getDetailPreTyped()
	{
		return detailPreTyped;
	}


	public void setDetailPreTyped(String detailPreTyped)
	{
		this.detailPreTyped = detailPreTyped;
	}


	public String getErrorMessagePreTyped()
	{
		return errorMessagePreTyped;
	}


	public void setErrorMessagePreTyped(String errorMessagePreTyped)
	{
		this.errorMessagePreTyped = errorMessagePreTyped;
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
		this.refNumber = refNumber;
	}
	
}
