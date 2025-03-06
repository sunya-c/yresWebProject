package com.sunya.yresWebProject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PrintError
{
	public static void println(String errText)
	{
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
		String formattedTime = time.format(timeFormat);
		
		System.err.print("\n" + formattedTime);
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
	public static String toErrorPage(HttpSession session, Object obj, Exception e)
	{
		session.setAttribute("errorDescription", e);
		session.setAttribute("fromServlet", obj.toString());
		return "error";
	}
	
	// to be deleted when conversion to Spring mvc is complete
	public static void toErrorPage(HttpSession session, HttpServletResponse response, Object obj, Exception e) throws IOException
	{
		session.setAttribute("errorDescription", e);
		session.setAttribute("fromServlet", obj.toString());
		response.sendRedirect("error");
	}
}
