package com.sunya.yresWebProject.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.models.DataError;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerError extends Controller1 implements ErrorController
{
	@GetMapping("/error")
	public String errorDefaultGet(Model model)
	{
		DataError dataError = new DataError();
		dataError.setErrorDescription("Something went wrong!");
		
		model.addAttribute(dataError);
		
		return Page.error;
	}
	@PostMapping("/error")
	public String errorDefaultPost(Model model)
	{
		return errorDefaultGet(model);
	}
	
	
	
	@GetMapping("/yresError")
	public String toErrorPage(Model model, HttpServletRequest request)
	{
		String errorDescription = request.getParameter("errorDescription");
		
		if (errorDescription!=null)
		{
			DataError dataError = new DataError();
			dataError.setErrorDescription(errorDescription);
			
			model.addAttribute(dataError);
			
			return Page.error;
		}
		else
			return redirect+Url.feedback;
	}
}
