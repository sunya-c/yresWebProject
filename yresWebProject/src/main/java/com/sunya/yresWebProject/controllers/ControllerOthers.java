package com.sunya.yresWebProject.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.filters.FilterBot;
import com.sunya.yresWebProject.rest.repositories.models.ModelIPBlacklist;

import jakarta.servlet.http.HttpServletResponse;

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
	
	@GetMapping("/healthcheck")
	public ResponseEntity<Void> healthcheck()
	{
		return ResponseEntity.status(200).build();
	}
	
	@Autowired
	FilterRegistrationBean<FilterBot> filterBot;
	@Autowired
	DaoIPBlacklist dao;
	
	@GetMapping("/saveBotstodatabase")
	public ResponseEntity<String> saveBotsToDatabase(HttpServletResponse response)
	{
		FilterBot.CustomArrayList tempBlacklist;
		FilterBot.CustomArrayList cachedBlacklist;
		ArrayList<ModelIPBlacklist> existing = new ArrayList<>();
		ArrayList<ModelIPBlacklist> newlyAdded = new ArrayList<>();
		ArrayList<ModelIPBlacklist> cachedAdded = new ArrayList<>();
		try
		{
			tempBlacklist = filterBot.getFilter().getTempBlacklist();
			cachedBlacklist = filterBot.getFilter().getCachedBlacklist();
			synchronized (tempBlacklist.getKeySynchronized())
			{
				tempBlacklist.stream().forEach(model -> {
					if (dao.isBlacklisted(model.getIpAddress()))
					{
						dao.incrementCounter(model.getIpAddress(), model.getBlockCount());
						existing.add(model);
					}
					else
					{
						dao.addToBlacklist(model.getIpAddress(), model.getCountryCode(), model.getBlockCount());
						newlyAdded.add(model);
					}
				});
				tempBlacklist.clear();
			}
			synchronized (cachedBlacklist.getKeySynchronized())
			{
				cachedBlacklist.stream().forEach(model -> {
					try
					{
						dao.incrementCounter(model.getIpAddress(), model.getBlockCount());
					}
					catch (Exception e)
					{
						System.err.println("error in cachedBlacklist: ip="+model.getIpAddress()+", blockCount="+model.getBlockCount());
						throw e;
					}
					ModelIPBlacklist newModel = new ModelIPBlacklist();
					newModel.setIpAddress(model.getIpAddress());
					newModel.setBlockCount(model.getBlockCount());
					cachedAdded.add(newModel);
					model.setBlockCount(0);
				});
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.toString());
		}
		
		return ResponseEntity.status(200)
							.body("tempBlacklists: "+tempBlacklist.toString()+"<br><br>"
								+ "existing items: "+existing.toString()+"<br><br>"
								+ "new items: "+newlyAdded.toString()+"<br><br>"
								+ "cached items: "+cachedAdded.toString());
	}
}









