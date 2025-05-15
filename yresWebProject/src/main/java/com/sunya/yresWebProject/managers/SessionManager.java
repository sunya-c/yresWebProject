package com.sunya.yresWebProject.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.managers.sessionObjects.SessionAccountInfo;
import com.sunya.yresWebProject.managers.sessionObjects.SessionAdminPanel;
import com.sunya.yresWebProject.managers.sessionObjects.SessionCreateAccount;
import com.sunya.yresWebProject.managers.sessionObjects.SessionFeedback;
import com.sunya.yresWebProject.managers.sessionObjects.SessionLogin;
import com.sunya.yresWebProject.managers.sessionObjects.SessionRedirecting;
import com.sunya.yresWebProject.managers.sessionObjects.SessionWeb;
import com.sunya.yresWebProject.synchronizedKeys.KeyHolder;

import jakarta.servlet.http.HttpSession;

/**
 * The idea of SessionManager is to manage session attributes efficiently. This
 * class provides you objects stored in session, which means you can get those
 * objects by <strong>Getters</strong> and don't have to worry about session
 * attribute names. It also provides quality-of-life methods which clear the
 * values of particular objects which belong to some specific group at once,
 * such as {@code removeLoginState()}, which basically removes all values that
 * define the client's login state.
 */
@Component
public class SessionManager
{
	@Autowired
	private HttpSession session;

	// Attribute names Feedback
	public static final String FEEDBACK_ERRORMESSAGE_PRETYPED = "preTypedFeedbackErrorMessage"; // param key name


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
	
	
	public SessionAdminPanel getSessionAdminPanel()
	{
		return (SessionAdminPanel)session.getAttribute("sessionAdminPanel");
	}
	
	
	public void createSessionAdminPanel()
	{
		session.setAttribute("sessionAdminPanel", new SessionAdminPanel());
	}
	
	
	public SessionAccountInfo getSessionAccountInfo()
	{
		return (SessionAccountInfo)session.getAttribute("sessionAccountInfo");
	}
	
	public void createSessionAccountInfo()
	{
		session.setAttribute("sessionAccountInfo", new SessionAccountInfo());
	}
	// end -- Attribute objects


	/**
	 * Clear ALL values related to <strong>Login form</strong> in
	 * {@code SessionLogin} object including:<br>
	 * <br>
	 * 1. usernamePreTyped <br>
	 * <br>
	 * 2. fromPage <br>
	 * <br>
	 * 3. usernameErr <br>
	 * <br>
	 * 4. passwordErr
	 */
	public void clearLoginForm()
	{
		synchronized (getKeyHolder().getKeyLogin())
		{
			getSessionLogin().setUsernamePreTyped(null);
			getSessionLogin().setFromPage(null);
			getSessionLogin().setUsernameErr(null);
			getSessionLogin().setPasswordErr(null);
		}
	}


	/**
	 * Clear <i>Login State</i> values in {@code SessionLogin} object, which is
	 * equivalent to <strong>Logout</strong>, including:<br>
	 * <br>
	 * 1. username <br>
	 * <br>
	 * 2. loggedIn
	 */
	public void clearLoginState()
	{
		synchronized (getKeyHolder().getKeyLogin())
		{
			getSessionLogin().setUsername(null);
			getSessionLogin().setLoggedIn(false);
		}

	}
}
