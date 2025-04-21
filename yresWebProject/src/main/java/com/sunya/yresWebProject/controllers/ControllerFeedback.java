package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoFeedback;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataFeedback;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.services.ServiceFeedback;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerFeedback extends Controller1
{
	/**
	 * The Controller for URL pattern 'feedback'.
	 * 
	 * @param md
	 * @param request
	 * @return Request dispatcher to <strong>FeedbackPage.jsp</strong>.
	 */
	@GetMapping("/feedback")
	public String feedbackPage(Model md, HttpServletRequest request)
	{
		DataFeedback dataFeedback = new DataFeedback();
		dataFeedback.setSubmittedFeedback(false);
		md.addAttribute(dataFeedback);
		
		try
		{
			String code = request.getParameter("code"); // The reference code for retrieving data from the session.
			String errorMessage = request.getParameter(SessionManager.FEEDBACK_ERRORMESSAGE_PRETYPED);

			if (code!=null)
			{
				// The data stored in the session is DataFeedback type.
				dataFeedback = sm.getSessionFeedback().consumeCode(code);

				if (dataFeedback!=null)
				{
					dataFeedback.setSubmittedFeedback(false);
					md.addAttribute(dataFeedback); // for viewing purpose.
				}
				else
				{
					UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("")
																		.path(Url.feedback);
					
					if (errorMessage!=null && !errorMessage.isBlank())
						builder.queryParam(SessionManager.FEEDBACK_ERRORMESSAGE_PRETYPED, errorMessage);
					
					String feedbackUrl = builder.encode().build().toUriString();
										
					return redirect + feedbackUrl;
				}
			}
			
			return Page.feedback;
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}
	
	@GetMapping("/feedback/summary")
	public String feedbackSummaryPage(Model md, DaoFeedback dao, HttpServletRequest request)
	{
		try
		{
			String codeSummary = request.getParameter("codeSummary");
			
			if (codeSummary!=null)
			{
				DataFeedback dataFeedback = sm.getSessionFeedback().consumeCode(codeSummary);
				
				// refNumber != null if and only if feedback is submitted successfully in ServiceFeedback
				if (dataFeedback != null && dataFeedback.getRefNumber()!=null)
				{
					dataFeedback.setSubmittedFeedback(true);
					md.addAttribute(dataFeedback);
					return Page.feedback;
				}
			}
			
			return redirect+Url.feedback;
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}
	
	
	@Autowired
	private ServiceFeedback sf;


	/**
	 * The Controller for URL pattern 'sFeedback'. This is for 'Submit Feedback'
	 * button in the Feedback page.
	 * 
	 * @param formFb
	 * @return Redirect to <strong>Redirecting page</strong> if succeeded.<br>
	 *         Redirect to <strong>Feedback page</strong> if the input detail is not
	 *         satisfied.<br>
	 *         Redirect to <strong>Error page</strong> if an Exception is thrown.
	 */
	@PostMapping("/sFeedback")
	public String sFeedback(FormFeedback formFb)
	{
		try
		{
			DataFeedback dataFeedback = new DataFeedback();

			String codeFeedback = sm.getSessionFeedback().generateCode(dataFeedback);

			return redirect + sf.sFeedback(formFb, dataFeedback, codeFeedback);
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}
}
