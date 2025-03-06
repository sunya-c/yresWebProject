package com.sunya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.services.ServiceDownloadResume;

@Controller
public class ControllerDownloadResume extends Controller1
{
	@Autowired
	private ServiceDownloadResume sdr;
	
	@GetMapping("/sDownloadResume")
	public ResponseEntity<Resource> sDownloadResume()
	{	
		return sdr.sDownloadResume();
	}
}
