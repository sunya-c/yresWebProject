package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerRedirecting extends Controller1
{
	@GetMapping("/redirecting")
	public String redirectingPage()
	{
		if (session.getAttribute(sm.FROM_SERVLET) != null)
		{
			System.out.println("fromServlet matched. Redirecting...");
			sm.removeFromServlet();
			
			return "RedirectingPage";
		}
		else
		{
			System.err.println("fromServlet mismatched. Redirect rejected.");
			sm.removeFromServlet();

			return redirect+"Home";
		}
		
	}
}
