package com.sunya.yresWebProject.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.DataRestApi;

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
	
	@GetMapping("/restApi/sSendRequest")
	@ResponseBody
	public ResponseEntity<String> sSendRequest(HttpServletRequest request) throws URISyntaxException, SomethingWentWrongException
	{
		String restMethod = request.getParameter("restMethod");
		String restUrl = request.getParameter("restUrl");
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
		
		System.out.println(url.getAuthority()+"  &&&  "+env.getProperty("SERY_WEB_DOMAIN"));
		String domain = url.getAuthority();
		if (domain==null || !domain.equals(env.getProperty("SERY_WEB_DOMAIN")))
		{
			throw new SomethingWentWrongException("<br>Domain name not allowed: "+restUrl);
		}
		
		HttpMethod method = HttpMethod.valueOf(restMethod.toUpperCase());
		System.out.println("rest URL : "+url.toString()+"  &&&  rest Method : "+method.name());
		
		RequestEntity<Void> requestEntity = RequestEntity.method(method, url)
														.header(HttpHeaders.ACCEPT, "application/xml, application/json")
														.header("fromTryMe", "true")
														.build();
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(requestEntity, String.class);
	}
}
