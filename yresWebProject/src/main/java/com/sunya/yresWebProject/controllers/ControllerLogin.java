package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.FormLogin;
import com.sunya.yresWebProject.services.ServiceLogin;
import com.sunya.yresWebProject.services.ServiceLogout;

@Controller
public class ControllerLogin extends Controller1
{
	@Autowired
	private ServiceLogin sli;


	/**
	 * The Controller for URL pattern 'sLogin'. This is for 'Log in' button in any
	 * Login form.<br>
	 * <br>
	 * The definition of <strong>issue page</strong>: The page that an action is
	 * taken. For example, if the client is logging in from Home page, the issue
	 * page in this case is Home page.
	 * 
	 * @param formL
	 * @return <strong>Log in</strong> and redirect to the <strong>issue
	 *         page</strong> if succeeded.<br>
	 *         Redirect to the <strong>issue page</strong> (NOT logged in) if login
	 *         detail is not satisfied.<br>
	 *         Redirect to the <strong>Error page</strong> if an Exception is
	 *         thrown.
	 */
	@PostMapping("/sLogin")
	public String sLogin(FormLogin formL)
	{
		try
		{
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				if (sm.getSessionLogin().isLoggedIn())
					throw new SomethingWentWrongException("<br>You're already logged in as '<i>"
												+sm.getSessionLogin().getUsername()+"</i>', "
												+"please log out first before logging in to a different account.");
				else
					return redirect + sli.sLogin(formL);
			}
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}


	@Autowired
	private ServiceLogout slo;


	/**
	 * The Controller for URL pattern 'sLogout'. This is for any 'Log out' button in
	 * the website.<br>
	 * <br>
	 * Clear all <strong>Login</strong> session-attribute.<br>
	 * <br>
	 * The definition of <strong>issue page</strong>: The page that an action is
	 * taken. For example, if the client is logging out from Home page, the issue
	 * page in this case is Home page.
	 * 
	 * @return Redirect to the <strong>issue page</strong> if succeeded.<br>
	 *         Redirect to the <strong>Error page</strong> if an Exception is
	 *         thrown.
	 */
	@PostMapping("/sLogout")
	public String sLogout()
	{
		try
		{
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				return redirect + slo.sLogout();
			}
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}
}
