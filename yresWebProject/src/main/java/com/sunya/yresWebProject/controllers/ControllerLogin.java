package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.services.ServiceLogin;
import com.sunya.yresWebProject.services.ServiceLogout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerLogin extends Controller1
{
	@Autowired
	private ServiceLogin sli;
	
	@PostMapping("/sLogin")
	public String sLogin(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			return redirect+sli.sLogin(request, response);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
	
	@Autowired
	private ServiceLogout slo;
	
	@PostMapping("/sLogout")
	public String sLogout(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			return redirect+slo.sLogout(request, response);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
}
