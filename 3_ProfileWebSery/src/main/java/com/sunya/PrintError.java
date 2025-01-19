package com.sunya;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
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
	
	public static void toErrorPage(HttpSession session, HttpServletResponse response, HttpServlet servlet, Exception e) throws IOException
	{
		session.setAttribute("errorDescription", e);
		session.setAttribute("fromServlet", servlet.getServletName());  // TODO: check if this works or not!!! The type is HttpServlet!!!
		response.sendRedirect("ErrorPage.jsp");
	}
	
	public static void toErrorPage(HttpSession session, HttpServletResponse response, HttpFilter filter, Exception e) throws IOException
	{
		session.setAttribute("errorDescription", e);
		session.setAttribute("fromServlet", filter.getFilterName());
		response.sendRedirect("ErrorPage.jsp");
	}

}
