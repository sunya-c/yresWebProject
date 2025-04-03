package com.sunya.yresWebProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourcePath implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry reg)
	{
		reg.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/");
		reg.addResourceHandler("/resources/pics/**").addResourceLocations("/resources/pics/");
	}
}
