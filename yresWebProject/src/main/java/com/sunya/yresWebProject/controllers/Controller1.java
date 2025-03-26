package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Controller1
{
	/**
	 * Use this String as a <strong>prefix</strong> for the URLs in Spring MVC
	 * Controllers to <strong>redirect</strong> instead of the default request
	 * dispatcher.
	 */
	protected static final String redirect = "redirect:";

	@Autowired
	protected HttpSession session;
	@Autowired
	protected SessionManager sm;


	/**
	 * This method sets the <strong>header</strong> of the given response object to
	 * tell the browser to NOT cache the page sent with this response. As a result,
	 * the page CANNOT be reached with the back button, or it has to be reloaded
	 * once again.
	 * 
	 * @param response ~ The response object of the current request.
	 */
	protected void preventBackButton(HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
		response.setHeader("Pragma", "no-cache"); // http 1.0
		response.setHeader("Expires", "0"); // Proxies ***Someone has said "0" will sometimes not work, instead set it
											// to be a date in the past.
	}
}
