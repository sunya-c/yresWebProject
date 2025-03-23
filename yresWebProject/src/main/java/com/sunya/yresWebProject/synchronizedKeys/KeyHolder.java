package com.sunya.yresWebProject.synchronizedKeys;

/**
 * Think of this class as a keyholder. It holds the keys (locks)
 * for threads to access synchronized blocks in one place, so you
 * don't have to worry about which key to use in each particular
 * situation.
 */
public class KeyHolder
{
	private final Object keyLogin = new Object();
	private final Object keyRedirecting = new Object();
	private final Object keyCreateAccount = new Object();
	private final Object keyFeedback = new Object();
	
	
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
}
