package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.models.DataFeedback;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.services.ServiceFeedback;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerFeedback extends Controller1
{
	@GetMapping("/feedback")
	public String feedbackPage(Model md, HttpServletRequest request)
	{
		try
		{
			String code = request.getParameter("code");
			String errorMessage = request.getParameter(sm.FEEDBACK_ERRORMESSAGE_PRETYPED);
			
			if (code!=null)
			{
				DataFeedback dataFeedback = sm.getSessionFeedback().consumeCode(code);
				
				if (dataFeedback!=null)
					md.addAttribute(dataFeedback);
				else
					return redirect+Url.feedback+"?"+((errorMessage==null||errorMessage.isBlank()) ? "" : sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+errorMessage);
			}
			
			return Page.feedback;
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
	
	@Autowired
	private ServiceFeedback sf;
	
	@PostMapping("/sFeedback")
	public String sFeedback(FormFeedback formFb)
	{
		try
		{
			DataFeedback dataFeedback = new DataFeedback();
			
			String codeFeedback = sm.getSessionFeedback().generateCode(dataFeedback);
			
			return redirect+sf.sFeedback(formFb, dataFeedback, codeFeedback);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
}
