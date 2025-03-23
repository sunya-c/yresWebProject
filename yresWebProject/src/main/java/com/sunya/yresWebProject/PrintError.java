package com.sunya.yresWebProject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import jakarta.servlet.http.HttpServletResponse;

public class PrintError
{
	public static void println(String errText)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now()
									.minus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS)
									.plusHours(7);
		String formattedTime = dateTime.format(dateTimeFormat);
		
		System.err.print(formattedTime);
		System.err.print(" ----> ");
		System.err.println(errText);
	}
	
	// for Spring mvc
	/**
	 * 
	 * @param session ~ the client's session.
	 * @param obj ~ the object of the class that is calling this method. Just pass {@code this} keyword.
	 * @param e ~ the error object. The error message belonging to this object will be displayed in the error page.
	 * @return "error" String.
	 * @throws IOException
	 */
	public static String toErrorPage(Exception e)
	{
		System.out.println("PrintError: "+e);
		
		return Url.yresError+"?errorDescription="+e.toString();
	}
	
	// to be deleted when conversion to Spring mvc is complete
	public static void toErrorPage(HttpServletResponse response, Exception e) throws IOException
	{
		System.out.println("PrintError: "+e);
		
		response.sendRedirect(Url.yresError+"?errorDescription="+e.toString());
	}
}
