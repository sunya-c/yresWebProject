package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.services.ServiceFeedback;

@Controller
public class ControllerFeedback extends Controller1
{
	@GetMapping("/feedback")
	public String feedbackPage()
	{
		try
		{
			if (fs.isFromServlet("ServletFeedback"))
			{
				System.out.println("fromServlet and context matched.");
			}
			else
			{
				System.err.println("fromServlet mismatch");
				
				sm.removeFeedbackErr();
				sm.removeFeedbackPreTyped();
			}
			sm.removeFromServlet();
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
		return "FeedbackPage";
	}
	
	@Autowired
	private ServiceFeedback sf;
	
	@PostMapping("/sFeedback")
	public String sFeedback(FormFeedback formFb)
	{		
		try
		{
			return redirect+sf.sFeedback(formFb);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
}
