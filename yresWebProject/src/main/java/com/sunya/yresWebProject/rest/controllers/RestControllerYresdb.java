package com.sunya.yresWebProject.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunya.yresWebProject.rest.exceptions.YresFileNotFound404Exception;
import com.sunya.yresWebProject.rest.repositories.RepoFeedback;
import com.sunya.yresWebProject.rest.repositories.RepoIPBlacklist;
import com.sunya.yresWebProject.rest.repositories.RepoLoginInfo;
import com.sunya.yresWebProject.rest.repositories.RepoPersinfo;
import com.sunya.yresWebProject.rest.repositories.models.ModelFeedback;
import com.sunya.yresWebProject.rest.repositories.models.ModelIPBlacklist;
import com.sunya.yresWebProject.rest.repositories.models.ModelLoginInfo;
import com.sunya.yresWebProject.rest.repositories.models.ModelPersinfo;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/rest")
public class RestControllerYresdb
{
	@RestController
	@RequestMapping(path = "/api/rest/user", produces = {"application/xml", "application/json"})
	public class RestUser
	{
		@Autowired
		RepoLoginInfo repoLogin;
		
		@GetMapping(path = {"","/"})
		public List<ModelLoginInfo> getUsers(HttpServletRequest request)
		{
			System.out.println("Accept: "+request.getHeader("Accept"));
			List<ModelLoginInfo> users = repoLogin.getUsers();
			
			return users;
		}
		
		@GetMapping(path = "/{username}")
		public ModelLoginInfo getUser(@PathVariable String username) throws YresFileNotFound404Exception
		{
			ModelLoginInfo user = repoLogin.getUser(username);
			if (user!=null)
				return user;
			else
				throw new YresFileNotFound404Exception();
		}
	}
	
	@RestController
	@RequestMapping(path = "/api/rest/feedback", produces = {"application/xml", "application/json"})
	public class RestFeedback
	{
		@Autowired
		RepoFeedback repoFeedback;
		
		@GetMapping(path = "/{refNumber}")
		public ModelFeedback getFeedback(@PathVariable String refNumber) throws YresFileNotFound404Exception
		{
			ModelFeedback feedback = repoFeedback.getFeedback(refNumber);
			if (feedback!=null)
				return feedback;
			else
				throw new YresFileNotFound404Exception();
		}
	}
	
	@RestController
	@RequestMapping(path = "/api/rest/persinfo", produces = {"application/xml", "application/json"})
	public class RestPersinfo
	{
		@Autowired
		RepoPersinfo repoPersinfo;
		
		@GetMapping({"", "/"})
		public ModelPersinfo getPersinfo() throws YresFileNotFound404Exception
		{
			ModelPersinfo persinfo = repoPersinfo.getPersinfoMinimal(1);
			if (persinfo!=null)
				return persinfo;
			else
				throw new YresFileNotFound404Exception();
		}
		
		@GetMapping("/fullversion")
		public ModelPersinfo getPersinfoFull() throws YresFileNotFound404Exception
		{
			ModelPersinfo persinfo = repoPersinfo.getPersinfoFullVersion(1);
			if (persinfo!=null)
				return persinfo;
			else
				throw new YresFileNotFound404Exception();
		}
	}
	
	@RestController
	@RequestMapping(path = "/api/rest/ipblacklist")
	public class RestIPBlacklist
	{
		@Autowired
		RepoIPBlacklist repoIP;
		
		@GetMapping({"","/"})
		public ArrayList<ModelIPBlacklist> getIPBlacklists()
		{
			return repoIP.getIPs();
		}
		
		@GetMapping("/{ipAddress}")
		public DoesExistIPBlacklistObj doesExistIPBlacklist(@PathVariable String ipAddress)
		{
			DoesExistIPBlacklistObj obj = new DoesExistIPBlacklistObj();
			obj.setIsBlacklisted(repoIP.isBlacklisted(ipAddress));
			return obj;
		}
		
		public class DoesExistIPBlacklistObj
		{
			private boolean isBlacklisted;
			
			public boolean isIsBlacklisted()
			{
				return isBlacklisted;
			}
			public void setIsBlacklisted(boolean isBlacklisted)
			{
				this.isBlacklisted = isBlacklisted;
			}
		}
	}
}
