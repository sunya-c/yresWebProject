package com.sunya.yresWebProject.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoWebdatainfo;
import com.sunya.yresWebProject.models.ModelWebdatainfo;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerHome extends Controller1
{
	@GetMapping("/")
	public String firstPage()
	{
		return "PreHomePage";
	}
	
	@Autowired
	Environment env;
	
	@GetMapping("/Home")
	public String homePage()
	{
		session.setAttribute(sm.LOGIN_FROMPAGE, "Home");
		
		session.setAttribute("trial1",
									!(System.getenv("SERY_DB_URL") == null || System.getenv("SERY_DB_URL").isBlank()));
		session.setAttribute("trial2",
									!(System.getProperty("SERY_DB_URL") == null || System.getProperty("SERY_DB_URL").isBlank()));

		setWebVersion();
							
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
	 * @throws SQLException
	 */
	private void setAnnouncement()
	{
		if (session.getAttribute(sm.WEB_NOTE1) == null || ((ModelWebdatainfo)session.getAttribute(sm.WEB_NOTE1)).getValue().isEmpty())
		{
			session.setAttribute(sm.WEB_NOTE1, dao.getWebinfo(sm.WEB_NOTE1));
		}
	}
	
	private void setWebVersion()
	{
		if (session.getAttribute(sm.WEB_VERSION) == null || ((String)session.getAttribute(sm.WEB_VERSION)).isEmpty())
		{
			session.setAttribute(sm.WEB_VERSION, env.getProperty("yres.version"));
		}
	}
}
