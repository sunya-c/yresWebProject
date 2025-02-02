package com.sunya.servlets;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sunya.managers.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ServletLogout
{
	public String sLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
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
