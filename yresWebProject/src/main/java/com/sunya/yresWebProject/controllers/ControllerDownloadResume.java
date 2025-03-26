package com.sunya.yresWebProject.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.services.ServiceDownloadResume;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerDownloadResume extends Controller1
{
	@Autowired
	private ServiceDownloadResume sdr;
	
	/**
	 * The Controller for 'sDownloadResume'. This is for 'Download Resume' button.
	 * 
	 * @param response
	 * @return The RESUME file.
	 * @throws IOException
	 */
	@GetMapping("/sDownloadResume")
	public ResponseEntity<Resource> sDownloadResume(HttpServletResponse response) throws IOException
	{
		return sdr.sDownloadResume();
	}

	
	
	
}
