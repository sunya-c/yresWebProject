package com.sunya.yresWebProject.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.cache.SimpleCache;

@Configuration
public class CustomConfig
{
	@Bean
	public IPinfo getIPinfo()
	{
		return new IPinfo.Builder()
						.setCache(new SimpleCache(Duration.ofDays(30)))
						.build();
	}
}
