package com.sunya.yresWebProject.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ServiceLogout
{
	@Autowired
	private SessionManager sm;
	
	public String sLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		sm.removeLoginState();
		System.out.println(toString());
		return "Home";
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
