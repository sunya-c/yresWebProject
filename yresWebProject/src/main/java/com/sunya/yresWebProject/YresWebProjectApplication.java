package com.sunya.yresWebProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sunya.yresWebProject.daos.DaoAutoRemove;
import com.sunya.yresWebProject.daos.DaoLoginInfo;

@SpringBootApplication
public class YresWebProjectApplication {

	public static ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
		context = SpringApplication.run(YresWebProjectApplication.class, args);
	}

}
