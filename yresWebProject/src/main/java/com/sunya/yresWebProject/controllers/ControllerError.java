package com.sunya.yresWebProject.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerError extends Controller1 implements ErrorController
{
	@GetMapping("/error")
	public String toErrorPageGet()
	{
		if (session.getAttribute(sm.FROM_SERVLET) == null)
			return redirect+"Home";
		else
		{
			sm.removeFromServlet();
			return "ErrorPage";
		}
	}
	@PostMapping("/error")
	public String toErrorPagePost()
	{
		return toErrorPageGet();
	}
}
