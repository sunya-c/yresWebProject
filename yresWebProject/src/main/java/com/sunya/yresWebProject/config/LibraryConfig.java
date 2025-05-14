package com.sunya.yresWebProject.config;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfig
{
	@Bean(name = "frontDate")
	public DateTimeFormatter getFormatDate()
	{
		return DateTimeFormatter.ofPattern("dd MMM yyyy");
	}
	
	@Bean(name = "backDate")
	public DateTimeFormatter getFormatDateBack()
	{
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	
	@Bean(name = "noSeparatorBackDate")
	public DateTimeFormatter getFormatDateBackNoSeparator()
	{
		return DateTimeFormatter.ofPattern("yyyyMMdd");
	}
	
	@Bean(name = "frontDateTime")
	public DateTimeFormatter getFormatDateTimeFront()
	{
		return DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
	}
	
	@Bean(name = "backDateTime")
	public DateTimeFormatter getFormatDateTimeBack()
	{
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	@Bean(name = "noSeparatorBackDateTime")
	public DateTimeFormatter getFormatDateTimeBackNoSeparator()
	{
		return DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	}
	
	@Bean(name = "serverTimeZone")
	public TimeZone getServerTimeZone()
	{
		return TimeZone.getDefault();
	}
}
