package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.rest.controllers.RestControllerYresdb;
import com.sunya.yresWebProject.rest.exceptions.YresFileNotFound404Exception;
import com.sunya.yresWebProject.rest.exceptions.YresMethodNotAllowed405Exception;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ServiceSendRequest
{
	@Autowired
	RestControllerYresdb.RestUser restUser;
	@Autowired
	RestControllerYresdb.RestFeedback restFeedback;
	@Autowired
	RestControllerYresdb.RestPersinfo restPersinfo;
	@Autowired
	RestControllerYresdb.RestIPBlacklist restIpBlacklist;
	
	public Object sSendRequest(HttpMethod method, String path, HttpServletRequest request) throws YresMethodNotAllowed405Exception, YresFileNotFound404Exception, SomethingWentWrongException
	{
		if (method.name()==null)
			throw new YresMethodNotAllowed405Exception();
		
		if (path==null || !path.startsWith("/api/rest") || path.equals("/api/rest") || path.equals("/api/rest/"))
			throw new YresFileNotFound404Exception();
		
		if (path.startsWith("/api/rest/user"))
		{
			if (path.equals("/api/rest/user") || path.equals("/api/rest/user/"))
			{
				if (method.name().equals("GET"))
					return restUser.getUsers(request);
				else
					throw new YresMethodNotAllowed405Exception();
			}
			if (path.startsWith("/api/rest/user/"))
			{
				if (method.name().equals("GET"))
					return restUser.getUser(path.substring("/api/rest/user/".length()));
				else
					throw new YresMethodNotAllowed405Exception();
			}
		}
		else if (path.startsWith("/api/rest/feedback"))
		{
			if (path.equals("/api/rest/feedback") || path.equals("/api/rest/feedback/"))
				throw new YresFileNotFound404Exception();
			if (path.startsWith("/api/rest/feedback/"))
			{
				if (method.name().equals("GET"))
					return restFeedback.getFeedback(path.substring("/api/rest/feedback/".length()));
				else
					throw new YresMethodNotAllowed405Exception();
			}
		}
		else if (path.startsWith("/api/rest/persinfo"))
		{
			if (path.equals("/api/rest/persinfo") || path.equals("/api/rest/persinfo/"))
			{
				if (method.name().equals("GET"))
					return restPersinfo.getPersinfo();
				else
					throw new YresMethodNotAllowed405Exception();
			}
			if (path.equals("/api/rest/persinfo/fullversion") || path.equals("/api/rest/persinfo/fullversion/"))
			{
				if (method.name().equals("GET"))
					return restPersinfo.getPersinfoFull();
				else
					throw new YresMethodNotAllowed405Exception();
			}
		}
		else if (path.startsWith("/api/rest/ipblacklist"))
		{
			if (path.equals("/api/rest/ipblacklist") || path.equals("/api/rest/ipblacklist/"))
			{
				if (method.name().equals("GET"))
					return restIpBlacklist.getIPBlacklists();
				else
					throw new YresMethodNotAllowed405Exception();
			}
			if (path.startsWith("/api/rest/ipblacklist/"))
			{
				if (method.name().equals("GET"))
					return restIpBlacklist.doesExistIPBlacklist(path.substring("/api/rest/ipblacklist/".length()));
				else
					throw new YresMethodNotAllowed405Exception();
			}
		}
		
		throw new YresFileNotFound404Exception();
	}
}
