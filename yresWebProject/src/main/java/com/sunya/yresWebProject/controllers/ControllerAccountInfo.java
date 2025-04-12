package com.sunya.yresWebProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.Page;

@Controller
public class ControllerAccountInfo extends Controller1
{
	@GetMapping("/accountInfo")
	public String accountInfoPage()
	{
		return Page.underConstruction;
	}
}
