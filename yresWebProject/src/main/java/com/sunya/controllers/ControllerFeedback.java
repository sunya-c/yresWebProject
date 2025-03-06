package com.sunya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.PrintError;
import com.sunya.services.ServiceFeedback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	public String sFeedback(HttpServletRequest request, HttpServletResponse response)
	{		
		try
		{
			return redirect+sf.sFeedback(request, response);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(session, this, e);
		}
	}
}
