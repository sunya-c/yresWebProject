package com.sunya.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.managers.SessionManager;

import jakarta.servlet.http.HttpSession;

@Component
public class ErrorMessageSetterFeedback
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	
	
	
	
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
