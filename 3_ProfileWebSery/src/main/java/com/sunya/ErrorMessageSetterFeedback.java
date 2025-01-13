package com.sunya;

import jakarta.servlet.http.HttpSession;

public class ErrorMessageSetterFeedback
{
	HttpSession session;
	
	String TITLE_ERR = "titleErr";
	String DETAIL_ERR = "detailErr";
	

	// Constructor
	public ErrorMessageSetterFeedback(HttpSession session)
	{
		this.session = session;
	}
	// end -- Constructor
	
	
	public void setReportTitleErr(ErrMsg errMessage)
	{
		session.setAttribute(TITLE_ERR, errMessage.toString());
	}
	
	public void setReportDetailErr(ErrMsg errMessage)
	{
		session.setAttribute(DETAIL_ERR, errMessage.toString());
	}
}
