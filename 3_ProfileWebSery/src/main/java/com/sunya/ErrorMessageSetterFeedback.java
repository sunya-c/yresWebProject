package com.sunya;

import jakarta.servlet.http.HttpSession;

public class ErrorMessageSetterFeedback
{
	private HttpSession session;
	private SessionManager sm;
	

	// Constructor
	public ErrorMessageSetterFeedback(HttpSession session)
	{
		this.session = session;
		sm = new SessionManager(session);
	}
	// end -- Constructor
	
	
	public void setFeedbackTitleErr(ErrMsg errMessage)
	{
		session.setAttribute(sm.FEEDBACK_TITLE_ERR, errMessage.toString());
	}
	
	public void setFeedbackDetailErr(ErrMsg errMessage)
	{
		session.setAttribute(sm.FEEDBACK_DETAIL_ERR, errMessage.toString());
	}
	
	public void setFeedbackErrorMessageErr(ErrMsg errMessage)
	{
		session.setAttribute(sm.FEEDBACK_ERRORMESSAGE_ERR, errMessage.toString());
	}
}
