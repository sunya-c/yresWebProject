package com.sunya.yresWebProject.synchronizedKeys;

/**
 * Think of this class as a keyholder. It holds the keys (locks) for threads to
 * access synchronized blocks, so you don't have to worry about which key to use
 * in each particular situation.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class KeyHolder
{
	private final Object keyLogin = new Object();
	private final Object keyRedirecting = new Object();
	private final Object keyCreateAccount = new Object();
	private final Object keyFeedback = new Object();
	private final Object keyAdminPanel = new Object();


	public Object getKeyLogin()
	{
		return keyLogin;
	}


	public Object getKeyRedirecting()
	{
		return keyRedirecting;
	}


	public Object getKeyCreateAccount()
	{
		return keyCreateAccount;
	}


	public Object getKeyFeedback()
	{
		return keyFeedback;
	}


	public Object getKeyAdminPanel()
	{
		return keyAdminPanel;
	}
}
