package com.sunya.yresWebProject.managers.sessionObjects;

import org.springframework.web.util.HtmlUtils;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;

/**
 * This class is for storing session-specific values related to
 * <strong>Login</strong>.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class SessionLogin
{
	private String fromPage = null;
	private String usernameErr = null;
	private String passwordErr = null;
	private String usernamePreTyped = null;

	private String username = null;
	private boolean loggedIn = false;


	public String getFromPage()
	{
		return fromPage;
	}


	public void setFromPage(String fromPage)
	{
		this.fromPage = fromPage;
	}


	public String getUsernameErr()
	{
		return usernameErr;
	}


	public void setUsernameErr(String usernameErr)
	{
		this.usernameErr = (usernameErr==null)? null : HtmlUtils.htmlEscape(usernameErr);
	}


	public String getPasswordErr()
	{
		return passwordErr;
	}


	public void setPasswordErr(String passwordErr)
	{
		this.passwordErr = (passwordErr==null)? null : HtmlUtils.htmlEscape(passwordErr);
	}


	public String getUsernamePreTyped()
	{
		return usernamePreTyped;
	}


	public void setUsernamePreTyped(String usernamePreTyped)
	{
		this.usernamePreTyped = (usernamePreTyped==null)? null : HtmlUtils.htmlEscape(usernamePreTyped);
	}


	public String getUsername()
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			return username;
		}
	}
	public String getUsernameUnescaped()
	{
		String username = getUsername();
		return (username==null)? null : HtmlUtils.htmlUnescape(username);
	}


	public void setUsername(String username)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			this.username = (username==null)? null : HtmlUtils.htmlEscape(username);
		}
	}


	public boolean isLoggedIn()
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			return loggedIn;
		}
	}


	public void setLoggedIn(boolean loggedIn)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			this.loggedIn = loggedIn;
		}
	}
}
