package com.sunya.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class[] { 
				WebConfig.class,
				ResourceConfig.class,
				LibraryConfig.class,
				InternalConfig.class};
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
}
