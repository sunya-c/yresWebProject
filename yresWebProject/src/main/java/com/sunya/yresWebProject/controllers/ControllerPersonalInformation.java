package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView persInfoPage(HttpServletResponse response)
	{
		session.setAttribute(sm.LOGIN_FROMPAGE, "personalInformation");
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("PersonalInformationPage");
		
		
		try
		{
			DataPersInfo myInfo = spinfo.sPersInfo();
			mv.addObject(myInfo);
		}
		catch (Exception e)
		{
			mv.setViewName(redirect+PrintError.toErrorPage(session, this, e));
			return mv;
		}
		
		preventBackButton(response);
		
		return mv;
	}
}
