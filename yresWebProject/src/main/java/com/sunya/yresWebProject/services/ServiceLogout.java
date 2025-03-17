package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiceLogout
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private HttpSession session;
	
	public String sLogout()
	{
		sm.removeLoginState();
		if (session.getAttribute(sm.LOGIN_FROMPAGE)==null)
			return "Home";
		else
			return (String)session.getAttribute(sm.LOGIN_FROMPAGE);
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
