package com.sunya.yresWebProject.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.DataRestApi;
import com.sunya.yresWebProject.rest.exceptions.YresFileNotFound404Exception;
import com.sunya.yresWebProject.rest.exceptions.YresMethodNotAllowed405Exception;
import com.sunya.yresWebProject.services.ServiceSendRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerRestApi extends Controller1
{
	@Autowired
	Environment env;
	
	@GetMapping("/restApi")
	public String restApiPage(Model md, HttpServletResponse response)
	{
		sm.getSessionLogin().setFromPage("restApi");
		
		DataRestApi dataRestApi = new DataRestApi();
		dataRestApi.setDomainName(env.getProperty("SERY_WEB_DOMAIN"));
		md.addAttribute(dataRestApi);
		
		preventBackButton(response);
		
		return Page.restApi;
	}
	
	@Autowired
	ServiceSendRequest ssr;
	
	@GetMapping("/restApi/sSendRequest")
	@ResponseBody
	public Object sSendRequest(HttpServletRequest request) throws URISyntaxException, SomethingWentWrongException, YresFileNotFound404Exception, YresMethodNotAllowed405Exception
	{
		String restMethod = request.getParameter("restMethod");
		String restUrl = request.getParameter("restUrl");
		if (restMethod==null)
			throw new SomethingWentWrongException("<br>Method cannot be null");
		if (restUrl==null)
			throw new SomethingWentWrongException("<br>URL cannot be null");
		
		restUrl = restUrl.replace("{", "%7B").replace("}", "%7D");
		
		URI url;
		try
		{
			url = new URI(restUrl);
		}
		catch (Exception e1)
		{
			try
			{
				url = new URI(UriComponentsBuilder.fromUriString(restUrl) // Try to encode if there's an exception (giving it a chance).
												.encode()
												.build()
												.toUriString());
			}
			catch (Exception e2)
			{
				throw new URISyntaxException(restUrl, "<br>Please check your URL");
			}
		}
		String protocol = (url.getScheme()==null)? null : url.getScheme().toLowerCase();
		String domain = (url.getAuthority()==null)? null : url.getAuthority().toLowerCase();
		String path = url.getPath();
		System.out.println(" => "+restMethod+" & "+protocol+" & "+domain+" & "+path+" &&& "+env.getProperty("SERY_WEB_DOMAIN"));
		if (protocol==null || domain==null || !protocol.equals("http") || !domain.equals(env.getProperty("SERY_WEB_DOMAIN")))
			throw new SomethingWentWrongException("<br>URL not allowed: "+restUrl);
		
		HttpMethod method = HttpMethod.valueOf(restMethod.toUpperCase());
		System.out.println("rest URL : "+url.toString()+"  &&&  rest Method : "+method.name());
		
		request.setAttribute("tryMePath", path);
		
		return ssr.sSendRequest(method, path, request);
	}
}


