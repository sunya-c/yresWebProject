package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;

@Controller
public class ControllerRestApi extends Controller1
{
	@GetMapping("/restApi")
	public String restApiPage()
	{
		return Page.underConstruction;
	}
}
