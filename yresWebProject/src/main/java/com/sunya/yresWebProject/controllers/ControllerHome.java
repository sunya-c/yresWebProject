package com.sunya.yresWebProject.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoWebdatainfo;
import com.sunya.yresWebProject.models.ModelWebdatainfo;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerHome extends Controller1
{
	@GetMapping("/")
	public String firstPage()
	{
		System.out.println(sm.getSession().getId()+"---"+sm.getKeyHolder());
		
		return Page.preHome;
	}
	
	@Autowired
	private Environment env;
	
	@GetMapping("/Home")
	public String homePage()
	{
		sm.getSessionLogin().setFromPage("Home");
		
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
			return redirect+PrintError.toErrorPage(e);
		}
		
		if (sm.getSessionLogin().isLoggedIn()==true)
		{
			return redirect+Url.welcome;
		}
		
		return Page.home;
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
			return redirect+PrintError.toErrorPage(e);
		}
		
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			if (sm.getSessionLogin().isLoggedIn())
			{
				preventBackButton(response);
				
				return Page.welcome;
			}
			else
				return redirect+Url.home;
		}
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
		if (sm.getSessionWeb().getWebNote1()==null || sm.getSessionWeb().getWebNote1().isEmpty())
		{
			ModelWebdatainfo model = dao.getWebinfo(DaoWebdatainfo.WEB_NOTE1);
			sm.getSessionWeb().setWebNote1(model.getValue());
		}
	}
	
	private void setWebVersion()
	{
		if (sm.getSessionWeb().getWebVersion()==null || sm.getSessionWeb().getWebVersion().isEmpty())
			sm.getSessionWeb().setWebVersion(env.getProperty("yres.version"));
	}
}
