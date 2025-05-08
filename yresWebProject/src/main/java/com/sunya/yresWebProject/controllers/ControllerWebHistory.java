package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;

@Controller
public class ControllerWebHistory extends Controller1
{
	@GetMapping("/webHistory")
	public String webHistoryPage()
	{
		sm.getSessionLogin().setFromPage("webHistory");
		
		return Page.webHistory;
	}
}
