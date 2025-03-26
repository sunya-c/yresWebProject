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
	
	/**
	 * The Controller for URL pattern 'personalInformation'.
	 * 
	 * @param response
	 * @param model
	 * @return Request dispatcher to <strong>PersonalInformationPage.jsp</strong>.
	 */
	@GetMapping("/personalInformation")
	public String persInfoPage(HttpServletResponse response, Model model)
	{
		sm.getSessionLogin().setFromPage("personalInformation");
		
		try
		{
			DataPersInfo dataPersInfo = new DataPersInfo();
			spinfo.sPersInfo(dataPersInfo);
			
			model.addAttribute(dataPersInfo);
			
			preventBackButton(response);
			
			return Page.persInfo;
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
}
