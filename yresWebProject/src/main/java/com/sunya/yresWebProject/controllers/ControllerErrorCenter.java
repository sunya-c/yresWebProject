package com.sunya.yresWebProject.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.rest.exceptions.YresFileNotFound404Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerErrorCenter extends Controller1
{
	
	@ExceptionHandler(exception = NoHandlerFoundException.class)
	public String fileNotFound404(Exception e, HttpServletRequest request, HttpServletResponse response) throws YresFileNotFound404Exception
	{
		System.out.println("in no handler found handler");
		System.out.println("PrintError: "+e);
		String errorMessage = "404 - NOT FOUND.<br><br>Please check your URL:<br>"+request.getRequestURL().toString();
		
		String yresErrorPath = UriComponentsBuilder.fromUriString("")
													.path(Url.yresError)
													.queryParam("errorDescription", errorMessage)
													.encode().build().toUriString();
		
		return redirect + yresErrorPath;
	}
	
//	@ExceptionHandler(exception = Exception.class)
//	public String globalError(Exception e)
//	{
//		System.out.println("PrintError: "+e);
//		return "redirect:/error";
//	}
}
