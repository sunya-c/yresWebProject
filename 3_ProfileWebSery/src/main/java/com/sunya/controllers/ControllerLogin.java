package com.sunya.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.PrintError;
import com.sunya.services.ServiceLogin;
import com.sunya.services.ServiceLogout;

import jakarta.servlet.ServletException;
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
