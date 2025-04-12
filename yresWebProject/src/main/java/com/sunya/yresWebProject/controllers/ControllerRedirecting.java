package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.models.DataRedirecting;

@Controller
public class ControllerRedirecting extends Controller1
{
	/**
	 * The Controller for URL pattern 'redirecting'.<br>
	 * <br>
	 * This page was designed to be reached by internal requests. User manually
	 * entering the URL will end up in <strong>Error page</strong>
	 * 
	 * @param md
	 * @param message
	 * @param destinationPage
	 * @param destinationUrl
	 * @param code
	 * @return Request dispatcher to <strong>RedirectingPage.jsp</strong> if the
	 *         code exists.<br>
	 *         Redirecting to <strong>Error page</strong> if the code does NOT
	 *         exists (client manually enters the URL).
	 */
	@GetMapping("/redirecting")
	public String redirectingPage(Model md, @RequestParam String message, @RequestParam String destinationPage,
								@RequestParam String destinationUrl, @RequestParam String code)
	{
		synchronized (sm.getKeyHolder().getKeyRedirecting())
		{
			if (sm.getSessionRedirecting().consumeCode(code))
			{
				System.out.println("Redirect code matched. Redirecting...");

				// Content on Redirecting Page
				DataRedirecting dataRedirecting = new DataRedirecting();
				dataRedirecting.setMessage(message);
				dataRedirecting.setDestinationPage(destinationPage);
				dataRedirecting.setDestinationUrl(destinationUrl);

				md.addAttribute(dataRedirecting);

				return Page.redirecting;
			}
			else
			{
				System.err.println("Redirect code mismatched. Redirect rejected.");

				return redirect+"error";
			}
		}
	}

}
