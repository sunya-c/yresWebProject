package com.sunya.yresWebProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class YresWebProjectApplication
{
	public static ConfigurableApplicationContext context;
	
	public static void main(String[] args)
	{
		context = SpringApplication.run(YresWebProjectApplication.class, args);
	}
}
