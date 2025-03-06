package com.sunya.yresWebProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintTime
{
	public static void println(String text)
	{
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.print(dateTime.format(dateTimeFormat));
		System.out.println(" "+text);
	}
}
