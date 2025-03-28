package com.sunya.yresWebProject.config;

import java.time.format.DateTimeFormatter;

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
}
