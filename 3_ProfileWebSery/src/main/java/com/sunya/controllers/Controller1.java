package com.sunya.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunya.servlets.ServletCreateAccount;
import com.sunya.servlets.ServletDownloadResume;
import com.sunya.servlets.ServletFeedback;
import com.sunya.servlets.ServletLogin;
import com.sunya.servlets.ServletLogout;
import com.sunya.servlets.ServletPersonalInformation;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class Controller1
{
	private String redirect = "redirect:";
	
	@RequestMapping("/Home")
	public ModelAndView homePage()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("LoginPage");
		mv.addObject("name", "value");  // same as request.setAttribute("name", "value");  get the value by request.getAttribute("name");
		
		return mv;
	}
	
	@GetMapping("/welcome")
	public String welcomePage()
	{
		return "WelcomePage";
	}
	
	
	
	
	@Autowired
	ServletLogin sli;
	
	@PostMapping("/sLogin")
	public String sLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		return redirect+sli.sLogin(request, response);
	}
	
	@Autowired
	ServletLogout slo;
	
	@PostMapping("/sLogout")
	public String sLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		return redirect+slo.sLogout(request, response);
	}
	
	
	
	
	@GetMapping("/redirecting")
	public String redirectingPage()
	{
		return "RedirectingPage";
	}
	
	
	
	
	@GetMapping("/createAccount")
	public String createAccountPage()
	{
		return "CreateAccountPage";
	}
	
	@Autowired
	ServletCreateAccount sca;
	
	@PostMapping("/sCreateAccount")
	public String sCreateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		return redirect+sca.sCreateAccount(request, response);
	}
	
	
	
	
	@Autowired
	ServletPersonalInformation spinfo;
	
	@GetMapping("/personalInformation")
	public String persInfoPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		spinfo.sPersInfo(request, response);
		
		return "PersonalInformationPage";
	}
	
	
	
	
	@GetMapping("/feedback")
	public String feedbackPage()
	{
		return "FeedbackPage";
	}
	
	@Autowired
	ServletFeedback sf;
	
	@PostMapping("/sFeedback")
	public String sFeedback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{		
		return redirect+sf.sFeedback(request, response);
	}

	
	
	
	@Autowired
	ServletDownloadResume sdr;
	
	@GetMapping("/sDownloadResume")
	public ResponseEntity<Resource> sDownloadResume()
	{	
		return sdr.sDownloadResume();
	}
	
	
	
	
	@GetMapping("/error")
	public String toErrorPageGet()
	{
		return "ErrorPage";
	}
	@PostMapping("/error")
	public String toErrorPagePost()
	{
		return "ErrorPage";
	}
	
}
