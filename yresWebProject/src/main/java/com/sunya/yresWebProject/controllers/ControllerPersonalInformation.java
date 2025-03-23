package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.models.DataPersInfo;
import com.sunya.yresWebProject.services.ServicePersonalInformation;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerPersonalInformation extends Controller1
{
	@Autowired
	private ServicePersonalInformation spinfo;
	
	@GetMapping("/personalInformation")
	public String persInfoPage(HttpServletResponse response, Model model)
	{
		sm.getSessionLogin().setFromPage("personalInformation");
		
		try
		{
			DataPersInfo myInfo = spinfo.sPersInfo();
			model.addAttribute(myInfo);
			
			preventBackButton(response);
			
			return Page.persInfo;
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
}
