package com.sunya.yresWebProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class PrintTime
{
	/**
	 * Pass a String here, and it will print the current date and time right before
	 * your text within the same line in the console.<br>
	 * <br>
	 * This method calls {@code System.out.print/println}, so text will be printed
	 * in <strong>white</strong> color.
	 * 
	 * @param text
	 */
	public static void println(String text)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now().minus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS)
									.plusHours(7);
		System.out.print(dateTime.format(dateTimeFormat));
		System.out.println(" "+text);
	}
}
