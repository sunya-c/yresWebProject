package com.sunya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunya.PrintError;
import com.sunya.daos.DaoWebdatainfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerHome extends Controller1
{
	@RequestMapping("/Home")
	public String homePage()
	{
		session.setAttribute(sm.LOGIN_FROMPAGE, "Home");
		
		try
		{
			setAnnouncement();
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
		
		if ((session.getAttribute("loggedIn") != null) && ((boolean) session.getAttribute("loggedIn") == true))
		{
			return redirect+"welcome";
		}
		
		return "LoginPage";
	}
	
	@GetMapping("/welcome")
	public String welcomePage(HttpServletResponse response)
	{
		try
		{
			setAnnouncement();
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
		
		if (session.getAttribute(sm.LOGIN_LOGGED_IN)!=null && ((boolean)session.getAttribute(sm.LOGIN_LOGGED_IN))==true)
		{
			preventBackButton(response);
			
			return "WelcomePage";
		}
		else
			return redirect+"Home";
	}
	
	@Autowired
	private DaoWebdatainfo dao;
	
	/**
	 * Set the Important note at the top the web page.
	 * 
	 * @return <strong>true</strong> ~ if retrieve data successfully.
	 * @throws ServletException
	 */
	private void setAnnouncement() throws ServletException
	{
		if (session.getAttribute(sm.WEB_NOTE1) == null || ((String)session.getAttribute(sm.WEB_NOTE1)).isEmpty())
		{
			session.setAttribute(sm.WEB_NOTE1, dao.getWebinfo(sm.WEB_NOTE1));
		}
	}
}
