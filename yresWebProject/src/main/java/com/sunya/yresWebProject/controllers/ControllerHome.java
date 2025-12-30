package com.sunya.yresWebProject.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.accountSecurity.UserAuthContext;
import com.sunya.yresWebProject.daos.DaoWebdatainfo;
import com.sunya.yresWebProject.models.ModelWebdatainfo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerHome extends Controller1
{
	/**
	 * The Controller for URL pattern '/'.
	 * 
	 * @return Request dispatcher to 'PreHomePage.jsp'.
	 */
	@GetMapping("/")
	public String firstPage(Model md, HttpServletRequest request)
	{
		String botParam = request.getParameter("bqweqwaisdiqwe");
		if (botParam!=null)
		{
			md.addAttribute("botParam", "?bqweqwaisdiqwe="+botParam);
		}
		return Page.preHome;
	}


	@Autowired
	private Environment env;
	@Autowired
	UserAuthContext userAuth;

	/**
	 * The Controller for URL pattern 'Home'.
	 * 
	 * @return Request dispatcher to <strong>WelcomePage.jsp</strong> if
	 *         {@code SessionManager.getSessionLogin.isLoggedIn() == true}.<br>
	 *         Request dispatcher to <strong>LoginPage.jsp</strong> if otherwise.
	 */
	@GetMapping("/home")
	public String homePage()
	{
		System.out.println(sm.getSession().getId().substring(0, 5)+"-"
									+sm.getSession().getId().substring(sm.getSession().getId().length() - 5)+"---"
									+sm.getKeyHolder().toString().substring(
																sm.getKeyHolder().toString().indexOf("KeyHolder@")));
		sm.getSessionLogin().setFromPage(Url.home);

		session.setAttribute("trial1", !(System.getenv("SERY_DB_URL")==null || System.getenv("SERY_DB_URL").isBlank()));
		session.setAttribute("trial2", !(System.getProperty("SERY_DB_URL")==null
									|| System.getProperty("SERY_DB_URL").isBlank()));

//		setWebVersion();
		setResumeDate();

//		try
//		{
//			setAnnouncement();
//		}
//		catch (Exception e)
//		{
//			return redirect + PrintError.toErrorPage(e);
//		}
		
		return Page.welcome;
	}
	
	@GetMapping("/login")
	public String loginPage()
	{
		return Page.login;
	}


//	/**
//	 * The Controller for URL pattern 'welcome'.
//	 * 
//	 * @param response
//	 * @return Request dispatcher to <strong>WelcomePage.jsp</strong> if
//	 *         {@code SessionManager.getSessionLogin.isLoggedIn() == true}.<br>
//	 *         Request dispatcher to <strong>LoginPage.jsp</strong> if otherwise.
//	 */
//	@GetMapping("/welcome")
//	public String welcomePage(HttpServletResponse response)
//	{
//		setResumeDate();
//
//		try
//		{
//			setAnnouncement(); // Retrieve data for the announcement at the top of the page from the database
//								// and set it to session.
//		}
//		catch (Exception e)
//		{
//			return redirect + PrintError.toErrorPage(e);
//		}
//
//		synchronized (sm.getKeyHolder().getKeyLogin())
//		{
//			preventBackButton(response);
//
//			return Page.welcome;
//		}
//	}


	@Autowired
	private DaoWebdatainfo dao;


	/**
	 * If it hasn't done yet, retrieve the <strong>announcement text</strong> from
	 * the database and set it to the session. The views can make use of this text
	 * by calling this attribute '{@code sessionWeb.webNote1}'.
	 */
	public void setAnnouncement()
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


	@Autowired
	@Qualifier("backDate")
	DateTimeFormatter dateFormatBack;
	
	@Autowired
	@Qualifier("frontDate")
	DateTimeFormatter dateFormatFront;
	/**
	 * If it hasn't done yet, retrieve the <strong>resume date</strong> from the
	 * database and set it to the session. The views can make use of this text by
	 * calling this attribute '{@code sessionWeb.resumeDate}'.
	 */
	private void setResumeDate()
	{
		ModelWebdatainfo model = dao.getWebinfo(DaoWebdatainfo.WEB_RESUME_DATE);
		String date = LocalDate.parse(model.getValue(), dateFormatBack).format(dateFormatFront);
		sm.getSessionWeb().setResumeDate(date);
	}
}
