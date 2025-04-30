package com.sunya.yresWebProject.models;

import org.springframework.web.util.HtmlUtils;

/**
 * A class created for viewing purpose. Use this class to display dynamic data
 * on <strong>Error page</strong>.
 */
public class DataError
{
	private String errorDescription = null;
	
	
	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		if (errorDescription!=null)
		{
			errorDescription = (errorDescription.contains("<br>"))? errorDescription : HtmlUtils.htmlEscape(errorDescription);
		}
		this.errorDescription = errorDescription;
	}
}
