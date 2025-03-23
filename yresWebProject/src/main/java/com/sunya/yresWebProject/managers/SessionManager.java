package com.sunya.yresWebProject.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.managers.sessionObjects.SessionCreateAccount;
import com.sunya.yresWebProject.managers.sessionObjects.SessionFeedback;
import com.sunya.yresWebProject.managers.sessionObjects.SessionLogin;
import com.sunya.yresWebProject.managers.sessionObjects.SessionRedirecting;
import com.sunya.yresWebProject.managers.sessionObjects.SessionWeb;
import com.sunya.yresWebProject.synchronizedKeys.KeyHolder;

import jakarta.servlet.http.HttpSession;

/**
 * The idea of SessionManager is to manage session attributes efficiently. 
 * This class provides you session attribute names in String type, 
 * so you can call those fields and don't have to worry about typos. 
 * It also provides quality of life methods which remove the values of particular 
 * attributes which belong to some specific group at once, such as 
 * {@code removeLoginState()} will basically remove all attributes which define 
 * the client's login state.
 * 
 * 
 * 
 */
@Component
public class SessionManager
{
	@Autowired
	private HttpSession session;
	
	
	// Attribute names Feedback
	public final String FEEDBACK_ERRORMESSAGE_PRETYPED = "preTypedFeedbackErrorMessage";   // param key name
	
	
	
	// Contructor
	public SessionManager()
	{
	}
	public SessionManager(HttpSession session)
	{
		this.session = session;
	}
	// end -- Constructor
	
	
	
	public HttpSession getSession()
	{
		return session;
	}
	
	
	// Attribute objects
	public String getInitializeString()
	{
		return (String)session.getAttribute("yresSessionInitializeString");
	}
	public void setSessionInitialized()
	{
		session.setAttribute("yresSessionInitializeString", "Session-initialized!!!");
	}
	
	
	public KeyHolder getKeyHolder()
	{
		return (KeyHolder)session.getAttribute("keyHolder");
	}
	public void createKeyHolder()
	{
		session.setAttribute("keyHolder", new KeyHolder());
	}
	
	
	public SessionFeedback getSessionFeedback()
	{
		return (SessionFeedback)session.getAttribute("sessionFeedback");
	}
	public void createSessionFeedback()
	{
		session.setAttribute("sessionFeedback", new SessionFeedback());
	}
	
	
	public SessionCreateAccount getSessionCreateAccount()
	{
		return (SessionCreateAccount)session.getAttribute("sessionCreateAccount");
	}
	public void createSessionCreateAccount()
	{
		session.setAttribute("sessionCreateAccount", new SessionCreateAccount());
	}
	
	
	public SessionLogin getSessionLogin()
	{
		return (SessionLogin)session.getAttribute("sessionLogin");
	}
	public void createSessionLogin()
	{
		session.setAttribute("sessionLogin", new SessionLogin());
	}
	
	
	public SessionWeb getSessionWeb()
	{
		return (SessionWeb)session.getAttribute("sessionWeb");
	}
	public void createSessionWeb()
	{
		session.setAttribute("sessionWeb", new SessionWeb());
	}
	
	
	public SessionRedirecting getSessionRedirecting()
	{
		return (SessionRedirecting)session.getAttribute("sessionRedirecting");
	}
	public void createSessionRedirecting()
	{
		session.setAttribute("sessionRedirecting", new SessionRedirecting());
	}
	// end -- Attribute objects
	
	
	/**
	 * Remove <i>ERROR</i> attributes related to 
	 * {@code ServletLogin.java}, 
	 * which include:
	 * <p>    "preTypedUsername",
	 * <p>    "fromPage",
	 * <p>    "wrongUsername",
	 * <p>    "wrongPassword"
	 */
	public void removeLoginErr()
	{
		getSessionLogin().setUsernamePreTyped(null);
		getSessionLogin().setFromPage(null);
		getSessionLogin().setUsernameErr(null);
		getSessionLogin().setPasswordErr(null);
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
		getSessionLogin().setUsername(null);
		getSessionLogin().setLoggedIn(false);
	}
}
