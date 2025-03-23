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
	
	@PostMapping("/sLogin")
	public String sLogin(FormLogin formL)
	{
		try
		{
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				if (sm.getSessionLogin().isLoggedIn())
					throw new SomethingWentWrongException("<br>You're already logged in as '<i>"+sm.getSessionLogin().getUsername()+"</i>', "
												+ "please log out first before logging in to a different account.");
				else
					return redirect+sli.sLogin(formL);
			}
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
	
	@Autowired
	private ServiceLogout slo;
	
	@PostMapping("/sLogout")
	public String sLogout()
	{
		try
		{
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				return redirect+slo.sLogout();
			}
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
}
