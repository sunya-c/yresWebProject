package com.sunya.yresWebProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class PrintTime
{
	public static void println(String text)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now()
									.minus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS)
									.plusHours(7);
		System.out.print(dateTime.format(dateTimeFormat));
		System.out.println(" "+text);
	}
}
