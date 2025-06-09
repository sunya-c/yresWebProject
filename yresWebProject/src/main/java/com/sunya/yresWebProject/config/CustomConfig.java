package com.sunya.yresWebProject.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.cache.SimpleCache;

@Configuration
public class CustomConfig
{
	@Autowired
	Environment env;
	
	@Bean
	public IPinfo getIPinfo()
	{
		return new IPinfo.Builder()
						.setToken(env.getRequiredProperty("yres.ipinfo.token"))
						.setCache(new SimpleCache(Duration.ofDays(30)))
						.build();
	}
}
