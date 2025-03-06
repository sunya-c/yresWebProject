package com.sunya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.PrintError;
import com.sunya.services.ServicePersonalInformation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerPersonalInformation extends Controller1
{
	@Autowired
	private ServicePersonalInformation spinfo;
	
	@GetMapping("/personalInformation")
	public String persInfoPage(HttpServletRequest request, HttpServletResponse response)
	{
		session.setAttribute(sm.LOGIN_FROMPAGE, "personalInformation");
		
		try
		{
			spinfo.sPersInfo(request, response);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
		
		preventBackButton(response);
		
		return "PersonalInformationPage";
	}
}
