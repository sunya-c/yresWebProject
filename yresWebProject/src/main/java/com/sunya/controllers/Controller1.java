package com.sunya.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.sunya.FromServlet;
import com.sunya.managers.SessionManager;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Controller1
{
	protected String redirect = "redirect:";
	
	@Autowired
	protected HttpSession session;
	@Autowired
	protected SessionManager sm;
	@Autowired
	protected FromServlet fs;
	
	
	
	
	protected void preventBackButton(HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
		response.setHeader("Pragma", "no-cache"); // http 1.0
		response.setHeader("Expires", "0"); // Proxies ***Someone has said "0" will sometimes not work, instead set it to be a date in the past.
	}
}
