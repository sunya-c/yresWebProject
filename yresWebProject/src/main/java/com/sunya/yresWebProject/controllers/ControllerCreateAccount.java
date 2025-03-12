package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.services.ServiceCreateAccount;

@Controller
public class ControllerCreateAccount extends Controller1
{
	@GetMapping("/createAccount")
	public String createAccountPage()
	{
		try
		{
			if (fs.isFromServlet("ServletCreateAccount"))
			{
				System.out.println("fromServlet and context matched.");
			}
			else
			{
				System.err.println("fromServlet mismatch");
				
				sm.removeCreateAccountErr();
			}
			sm.removeFromServlet();
			return "CreateAccountPage";
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
	
	@Autowired
	private ServiceCreateAccount sca;
	
	@PostMapping("/sCreateAccount")
	public String sCreateAccount(FormCreateAccount formCA)
	{
		try
		{
			return redirect+sca.sCreateAccount(formCA);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
	
}
