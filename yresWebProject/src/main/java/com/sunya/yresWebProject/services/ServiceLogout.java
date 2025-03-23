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
	
	
	
	public String sLogout()
	{
		sm.removeLoginState();
		
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
