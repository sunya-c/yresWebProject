package com.sunya.yresWebProject.accountSecurity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunya.yresWebProject.rest.repositories.models.ModelErrorReport;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerSecurity
{
	@Autowired
	@Qualifier("frontDateTime")
	DateTimeFormatter format;
	@Autowired
	@Qualifier("serverTimeZone")
	TimeZone tzone;
	
	
	@GetMapping("/badCredentials")
	@ResponseBody
	public ModelErrorReport badCredentials(HttpServletResponse response)
	{
		ModelErrorReport model = new ModelErrorReport();
		model.setTimestamp(LocalDateTime.now().minus(tzone.getRawOffset(), ChronoUnit.MILLIS).plusHours(7).format(format));
		response.setStatus(400);
		model.setError("Bad credentials, please check your Authorization header!");
		model.setPath("/basicLogin");
		return model;
	}

}
