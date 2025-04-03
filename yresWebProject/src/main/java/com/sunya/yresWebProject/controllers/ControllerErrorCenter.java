package com.sunya.yresWebProject.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sunya.yresWebProject.Url;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerErrorCenter
{
	@ExceptionHandler(exception = NoHandlerFoundException.class)
	public String fileNotFound404(Exception e, HttpServletRequest request)
	{
		System.out.println("PrintError: "+e);
		String errorMessage = "404 - NOT FOUND.<br><br>Please check your URL:<br>"+request.getRequestURL();
		
		return "redirect:/"+Url.yresError+"?errorDescription="+errorMessage;
	}
}
