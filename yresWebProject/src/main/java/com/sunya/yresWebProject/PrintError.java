package com.sunya.yresWebProject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import jakarta.servlet.http.HttpServletResponse;

public class PrintError
{
	/**
	 * Pass a String here, and it will print the current date and time right before
	 * your text within the same line in the console.<br>
	 * <br>
	 * This method calls {@code System.err.print/println}, so text will be printed
	 * in <strong>red</strong> color.
	 * 
	 * @param errText
	 */
	public static void println(String errText)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now().minus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS)
									.plusHours(7);
		String formattedTime = dateTime.format(dateTimeFormat);

		System.err.print(formattedTime);
		System.err.print(" ----> ");
		System.err.println(errText);
	}


	// for Spring mvc
	/**
	 * Call this method to redirect to the <strong>error page</strong> which shows
	 * the error message from the given Exception object.<br>
	 * <br>
	 * Call this method when the Exception is thrown <strong>inside</strong> the
	 * scope of Spring MVC <strong>Controller</strong>
	 * 
	 * @param e ~ The error message from this Exception object will be shown in the
	 *          <strong>error page</strong>.
	 * @return String of the <strong>error page</strong> name followed by
	 *         '{@code ?errorDescription=error message}' (so in total, you'll get
	 *         'errorPageName?errorDescription=errorMessage'). It can then be
	 *         returned in Spring MVC <strong>Controller</strong> to redirect to the
	 *         <strong>error page</strong>, noting that 'redirect:' has to be
	 *         mentioned explicitly, as this method returns only the name of the
	 *         error page.
	 * @throws IOException
	 */
	public static String toErrorPage(Exception e)
	{
		System.out.println("PrintError: "+e);

		return Url.yresError+"?errorDescription="+e.toString();
	}


	/**
	 * Call this method to redirect to the <strong>error page</strong> which shows
	 * the error message from the given Exception object.<br>
	 * <br>
	 * Call this method when the Exception is thrown <strong>outside</strong> the
	 * scope of Spring MVC <strong>controller</strong>, such as filter. This method
	 * will redirect to the <strong>error page</strong> by
	 * {@code response.sendRedirect()}
	 * 
	 * @param response ~ The response object of the current request.
	 * @param e        ~ The error message from this Exception object will be shown
	 *                 in the <strong>error page</strong>.
	 * @throws IOException
	 */
	public static void toErrorPage(HttpServletResponse response, Exception e) throws IOException
	{
		System.out.println("PrintError: "+e);

		response.sendRedirect(Url.yresError+"?errorDescription="+e.toString());
	}
}
