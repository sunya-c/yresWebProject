package com.sunya.yresWebProject.rest.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sunya.yresWebProject.rest.exceptions.YresFileNotFound404Exception;
import com.sunya.yresWebProject.rest.exceptions.YresMethodNotAllowed405Exception;
import com.sunya.yresWebProject.rest.repositories.models.ModelErrorReport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RestControllerErrorCenter
{
	@Autowired
	@Qualifier("frontDateTime")
	DateTimeFormatter format;
	
	@Autowired
	@Qualifier("serverTimeZone")
	TimeZone tzone;
	
	private ModelErrorReport commonTask(HttpStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ModelErrorReport error = new ModelErrorReport();
		error.setTimestamp(LocalDateTime.now().minus(tzone.getRawOffset(), ChronoUnit.MILLIS).plusHours(7).format(format));
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		
		String tryMePath = (String)request.getAttribute("tryMePath");
		if (tryMePath != null)
			error.setPath(tryMePath);
		else
			error.setPath(request.getRequestURI());
		return error;
	}
	
	@ExceptionHandler(exception = {YresFileNotFound404Exception.class, NoHandlerFoundException.class})
//	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ModelErrorReport fileNotFound404(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpStatus status = HttpStatus.valueOf(404);
		
		return commonTask(status, request, response);
	}
	
	@ExceptionHandler(exception = {YresMethodNotAllowed405Exception.class, HttpRequestMethodNotSupportedException.class})
//	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public ModelErrorReport methodNotAllowed405(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpStatus status = HttpStatus.valueOf(405);
		
		return commonTask(status, request, response);
	}
}
