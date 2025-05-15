package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.models.DataAccountInfo;
import com.sunya.yresWebProject.models.FormChangePassword;
import com.sunya.yresWebProject.services.ServiceAccountInfo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerAccountInfo extends Controller1
{
	@Autowired
	ServiceAccountInfo sai;
	
	@GetMapping("/accountInfo")
	public String accountInfoPage(Model md, HttpServletRequest request)
	{
		String code = request.getParameter("code");
		
		if (code==null)
			return Page.accountInfo;
		DataAccountInfo dataAccountInfo = sm.getSessionAccountInfo().consumeCode(code);
		if (dataAccountInfo==null)
			return redirect+Url.accountInfo;
		md.addAttribute(dataAccountInfo);
		return Page.accountInfo;
	}
	
	@PostMapping("/accountInfo/sChangePassword")
	public String sChangePassword(FormChangePassword formCP)
	{
		try
		{
			return redirect+sai.sChangePassword(formCP);
		}
		catch (Exception e)
		{
			return PrintError.toErrorPage(e);
		}
	}
}
