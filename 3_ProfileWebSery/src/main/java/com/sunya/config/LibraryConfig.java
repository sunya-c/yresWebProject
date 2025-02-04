package com.sunya.config;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;

public class LibraryConfig
{
	@Bean(name = "frontDate")
	public DateTimeFormatter getFormatDate()
	{
		return DateTimeFormatter.ofPattern("dd MMM yyyy");
	}
	
	@Bean(name = "backDateTime")
	public DateTimeFormatter getFormatDateTime()
	{
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
}
