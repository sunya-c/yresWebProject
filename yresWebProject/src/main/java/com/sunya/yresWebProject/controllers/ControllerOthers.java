package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerOthers extends Controller1
{
	@Autowired
	private ResourceLoader loader;
	
	@GetMapping("/favicon.ico")
	public ResponseEntity<Resource> favicon()
	{
		Resource resource = loader.getResource("/resources/pics/Icon.png");
		
		return ResponseEntity.ok()
							.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
							.body(resource);
	}
}
