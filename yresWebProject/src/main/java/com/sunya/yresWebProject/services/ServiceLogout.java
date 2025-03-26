package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.managers.SessionManager;

@Service
public class ServiceLogout
{
	@Autowired
	private SessionManager sm;
	
	
	/**
	 * Clear the Login-state-related values in the session.
	 * 
	 * @return <strong>String of the page URL</strong> that the user clicked 'Log out'.
	 */
	public String sLogout()
	{
		sm.clearLoginState();
		
		if (sm.getSessionLogin().getFromPage()==null)
			return Url.home;
		else
			return sm.getSessionLogin().getFromPage();
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
