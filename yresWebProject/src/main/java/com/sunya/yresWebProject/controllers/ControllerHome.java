package com.sunya.yresWebProject.controllers;

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
	/**
	 * The Controller for URL pattern '/'.
	 * 
	 * @return Request dispatcher to 'PreHomePage.jsp'.
	 */
	@GetMapping("/")
	public String firstPage()
	{
		return Page.preHome;
	}


	@Autowired
	private Environment env;


	/**
	 * The Controller for URL pattern 'Home'.
	 * 
	 * @return Request dispatcher to <strong>WelcomePage.jsp</strong> if
	 *         {@code SessionManager.getSessionLogin.isLoggedIn() == true}.<br>
	 *         Request dispatcher to <strong>LoginPage.jsp</strong> if otherwise.
	 */
	@GetMapping("/Home")
	public String homePage()
	{
		System.out.println(sm.getSession().getId()+"---"+sm.getKeyHolder());
		sm.getSessionLogin().setFromPage("Home");

		session.setAttribute("trial1", !(System.getenv("SERY_DB_URL")==null || System.getenv("SERY_DB_URL").isBlank()));
		session.setAttribute("trial2", !(System.getProperty("SERY_DB_URL")==null
									|| System.getProperty("SERY_DB_URL").isBlank()));

		setWebVersion();

		try
		{
			setAnnouncement();
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}

		if (sm.getSessionLogin().isLoggedIn()==true)
		{
			return redirect + Url.welcome;
		}

		return Page.home;
	}


	/**
	 * The Controller for URL pattern 'welcome'.
	 * 
	 * @param response
	 * @return Request dispatcher to <strong>WelcomePage.jsp</strong> if
	 *         {@code SessionManager.getSessionLogin.isLoggedIn() == true}.<br>
	 *         Request dispatcher to <strong>LoginPage.jsp</strong> if otherwise.
	 */
	@GetMapping("/welcome")
	public String welcomePage(HttpServletResponse response)
	{
		try
		{
			setAnnouncement(); // Retrieve data for the announcement at the top of the page from the database
								// and set it to session.
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}

		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			if (sm.getSessionLogin().isLoggedIn())
			{
				preventBackButton(response);

				return Page.welcome;
			}
			else
				return redirect + Url.home;
		}
	}


	@Autowired
	private DaoWebdatainfo dao;


	/**
	 * If it hasn't done yet, retrieve the <strong>announcement text</strong> from
	 * the database and set it to the session. The views can make use of this text
	 * by calling this attribute '{@code sessionWeb.webNote1}'.
	 */
	private void setAnnouncement()
	{
		if (sm.getSessionWeb().getWebNote1()==null || sm.getSessionWeb().getWebNote1().isEmpty())
		{
			ModelWebdatainfo model = dao.getWebinfo(DaoWebdatainfo.WEB_NOTE1);
			sm.getSessionWeb().setWebNote1(model.getValue());
		}
	}


	/**
	 * If it hasn't done yet, retrieve the <strong>web version</strong> from the
	 * <strong>Environment variables</strong> and set it to the session. The views
	 * can make use of this text by calling this attribute
	 * '{@code sessionWeb.webVersion}'.
	 */
	private void setWebVersion()
	{
		if (sm.getSessionWeb().getWebVersion()==null || sm.getSessionWeb().getWebVersion().isEmpty())
			sm.getSessionWeb().setWebVersion(env.getProperty("yres.version"));
	}
}
