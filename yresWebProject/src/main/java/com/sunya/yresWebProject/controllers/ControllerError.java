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
	/**
	 * The Controller for URL pattern 'error' or default Error page.
	 * 
	 * @param model
	 * @return Request dispatcher to <strong>ErrorPage.jsp</strong> with the
	 *         <strong>default</strong> error message.
	 */
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


	/**
	 * The Controller for URL pattern 'yresError'.<br>
	 * <br>
	 * To avoid conflict, it's recommended to request for this URL by:<br>
	 * <br>
	 * 1. returning {@code "redirect:"+PrintError.toErrorPage(Exception e)} to the
	 * Controller's method for Exception thrown <strong>inside</strong> the scope of
	 * Spring MVC Controller.<br>
	 * <br>
	 * 2. calling
	 * {@code PrintError.toErrorPage(HttpServletResponse response, Exception e)} for
	 * Exception thrown <strong>outside</strong> the scope of Spring MVC Controller,
	 * such as filter.
	 * 
	 * @param model
	 * @param request
	 * @return Request dispatcher to <strong>ErrorPage.jsp</strong> with
	 *         <strong>custom</strong> error message.
	 */
	@GetMapping("/yresError")
	public String toErrorPage(Model model, HttpServletRequest request)
	{
		String errorDescription = request.getParameter("errorDescription");

		if (errorDescription!=null)
		{
			DataError dataError = new DataError();
			dataError.setErrorDescription(errorDescription); // Set the error message shown on the page.

			model.addAttribute(dataError);

			return Page.error;
		}
		else
			return redirect + Url.feedback;
	}
}
