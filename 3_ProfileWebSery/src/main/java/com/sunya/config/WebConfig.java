package com.sunya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@EnableWebMvc
@ComponentScan("com.sunya")
public class WebConfig
{
	@Bean
	public InternalResourceViewResolver setView()
	{
		InternalResourceViewResolver view = new InternalResourceViewResolver();
		view.setPrefix("/WEB-INF/pages/");
		view.setSuffix(".jsp");
		
		return view;
	}

}
