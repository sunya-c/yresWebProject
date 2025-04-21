package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;

@Controller
public class ControllerAdminPanel extends Controller1
{
	@GetMapping("/adminPanel")
	public String adminPanelPage()
	{
		return Page.underConstruction;
	}
}
