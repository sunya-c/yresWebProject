package com.sunya;

import jakarta.servlet.http.HttpSession;

public class SessionManager
{
	HttpSession session;
	
	// Attribute names Feedback Error
	String TITLE_ERR = "titleErr";
	String DETAIL_ERR = "detailErr";
	String TITLE_PRETYPE = "preTypeTitle";
	String DETAIL_PRETYPE = "preTypeDetail";
	
	// Constructor
	public SessionManager(HttpSession session)
	{
		this.session = session;
	}
	// end -- Constructor
	
	
	public void removeLogin()
	{
		// TODO
	}
	
	public void removeAccountCreateErr()
	{
		// TODO
	}
	
	/**
	 * Remove <i>ERROR</i> attributes related to 
	 * {@code FeedbackPage.jsp} and {@code ServletFeedback.java}, 
	 * which include:
	 * <p>    "titleErr",
	 * <p>    "detailErr"
	 */
	public void removeFeedbackErr()
	{
		session.removeAttribute(TITLE_ERR);
		session.removeAttribute(DETAIL_ERR);
	}
	
	/**
	 * Remove <i>ALL</i> attributes related to 
	 * {@code FeedbackPage.jsp} and {@code ServletFeedback.java}, 
	 * which include:
	 * <p>    "preTypeTitle",
	 * <p>    "preTypeDetail"
	 */
	public void removeFeedback()
	{
		session.removeAttribute(TITLE_PRETYPE);
		session.removeAttribute(DETAIL_PRETYPE);
	}

}
