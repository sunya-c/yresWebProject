package com.sunya.yresWebProject.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionManager
{
	@Autowired
	HttpSession session;
	
	
	// Attribute names Feedback
	final public String FEEDBACK_TITLE_ERR = "titleErr";
	final public String FEEDBACK_DETAIL_ERR = "detailErr";
	final public String FEEDBACK_ERRORMESSAGE_ERR = "errorMessageErr";
	final public String FEEDBACK_TITLE_PRETYPED = "preTypedFeedbackTitle";
	final public String FEEDBACK_DETAIL_PRETYPED = "preTypedFeedbackDetail";
	final public String FEEDBACK_ERRORMESSAGE_PRETYPED = "preTypedFeedbackErrorMessage";
	
	// Attribute names Login
	final public String LOGIN_FROMPAGE = "fromPage";
	final public String LOGIN_UNAME_ERR = "wrongUsername";
	final public String LOGIN_PASS_ERR = "wrongPassword";
	final public String LOGIN_UNAME_PRETYPED = "preTypedUsername";
	final public String LOGIN_USERNAME = "username";
	final public String LOGIN_LOGGED_IN = "loggedIn";
	
	// Attribute names CreateAccount
	final public String CREATEACCOUNT_UNAME_PRETYPED = "preTypedCreateUsername";
	final public String CREATEACCOUNT_UNAME_ERR = "usernameErr";
	final public String CREATEACCOUNT_PASS_ERR1 = "passwordErr1";
	final public String CREATEACCOUNT_PASS_ERR2 = "passwordErr2";
	
	// Attribute names RedirectingPage
	final public String REDIRECT_MESSAGE = "message";
	final public String REDIRECT_DESTINATION = "destinationPage";
	
	// Attribute names LoginPage, WelcomePage
	final public String WEB_NOTE1 = "WEB_NOTE1";
	
	// Attribute names ErrorPage
	final public String ERROR_DESCRIPTION = "errorDescription";
	
	// Attribute names others
	final public String FROM_SERVLET = "fromServlet";
	
	
	// Contructor
	public SessionManager()
	{
		
	}
	public SessionManager(HttpSession session)
	{
		this.session = session;
	}
	// end -- Constructor
	
	
	/**
	 * Remove <i>ERROR</i> attributes related to 
	 * {@code ServletLogin.java}, 
	 * which include:
	 * <p>    "fromPage",
	 * <p>    "wrongUsername",
	 * <p>    "wrongPassword"
	 */
	public void removeLoginErr()
	{
		session.removeAttribute(LOGIN_UNAME_PRETYPED);
		session.removeAttribute(LOGIN_FROMPAGE);
		session.removeAttribute(LOGIN_UNAME_ERR);
		session.removeAttribute(LOGIN_PASS_ERR);
	}
	
	/**
	 * Remove <i>Login State</i> attributes,
	 * which equivalent to <i>Logout</i>, 
	 * which include:
	 * <p>    "username",
	 * <p>    "loggedIn"
	 */
	public void removeLoginState()
	{
		session.removeAttribute(LOGIN_USERNAME);
		session.removeAttribute(LOGIN_LOGGED_IN);
	}
	
	/**
	 * Remove <i>ERROR</i> attributes related to 
	 * {@code ServletCreateAccount.java}, 
	 * which include:
	 * <p>    "preTypedCreateUsername",
	 * <p>    "usernameErr",
	 * <p>    "passwordErr1",
	 * <p>    "passwordErr2"
	 */
	public void removeCreateAccountErr()
	{
		session.removeAttribute(CREATEACCOUNT_UNAME_PRETYPED);
		session.removeAttribute(CREATEACCOUNT_UNAME_ERR);
		session.removeAttribute(CREATEACCOUNT_PASS_ERR1);
		session.removeAttribute(CREATEACCOUNT_PASS_ERR2);
	}
	
	public void removeRedirectingPageTemp()
	{
		session.removeAttribute(REDIRECT_MESSAGE);
		session.removeAttribute(REDIRECT_DESTINATION);
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
		session.removeAttribute(FEEDBACK_TITLE_ERR);
		session.removeAttribute(FEEDBACK_DETAIL_ERR);
		session.removeAttribute(FEEDBACK_ERRORMESSAGE_ERR);
	}
	
	/**
	 * Remove <i>ALL</i> attributes related to 
	 * {@code FeedbackPage.jsp} and {@code ServletFeedback.java}, 
	 * which include:
	 * <p>    "preTypedTitle",
	 * <p>    "preTypedDetail"
	 */
	public void removeFeedbackPreTyped()
	{
		session.removeAttribute(FEEDBACK_TITLE_PRETYPED);
		session.removeAttribute(FEEDBACK_DETAIL_PRETYPED);
	}
	
	public void removeWebNote()
	{
		session.removeAttribute(WEB_NOTE1);
	}
	
	/**
	 * Remove <strong>ALL</strong> attributes related to
	 * {@code ErrorPage.jsp}
	 * which include:
	 * <p>    "errorDescription"
	 * 
	 */
	public void removeErrorPage()
	{
		session.removeAttribute(ERROR_DESCRIPTION);
	}
	
	/**
	 * Remove <strong>fromServlet</strong> attribute
	 * which include:
	 * <p>    "fromServlet"
	 */
	public void removeFromServlet()
	{
		session.removeAttribute(FROM_SERVLET);
	}

}
