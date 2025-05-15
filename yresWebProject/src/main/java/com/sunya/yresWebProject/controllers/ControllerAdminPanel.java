package com.sunya.yresWebProject.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoDownloadinfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.DataAdminPanel;
import com.sunya.yresWebProject.services.ServiceAdminPanel;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@Controller
public class ControllerAdminPanel extends Controller1
{
	@Autowired
	DaoDownloadinfo daoDl;
	
	@GetMapping("/adminPanel")
	public String adminPanelPage(Model md, HttpServletRequest request, HttpServletResponse response)
	{
		preventBackButton(response);
		DataAdminPanel dataAdminPanel;
		String code = request.getParameter("code");
		if (code==null)
		{
			dataAdminPanel = new DataAdminPanel();
			dataAdminPanel.setResumeModels(daoDl.getNames());
			md.addAttribute(dataAdminPanel);
			return Page.adminPanel;
		}
		dataAdminPanel = sm.getSessionAdminPanel().consumeCode(code);
		if (dataAdminPanel==null)
			return redirect+Url.adminPanel;
		dataAdminPanel.setResumeModels(daoDl.getNames());
		md.addAttribute(dataAdminPanel);
		return Page.adminPanel;
	}
	
	@Autowired
	ServiceAdminPanel sap;
	
	@PostMapping("/adminPanel/sAction")
	public String sAction(Model md, HttpServletRequest request, HttpServletResponse response)
	{
		System.err.println("in /sAction!!!");
		String action = request.getParameter("action");
		
		DataAdminPanel dataAdminPanel = new DataAdminPanel();
		String code = sm.getSessionAdminPanel().generateCode(dataAdminPanel);
		
		try
		{
			if ("saveBotstodatabase".equals(action))
			{
				sap.sActionSaveBotstodatabase(dataAdminPanel);
			}
			else if ("deleteBotsinusageinfo".equals(action))
			{
				sap.sActionDeleteBotsinusageinfo(dataAdminPanel);
			}
			else
			{
				dataAdminPanel.setActionResults("Invalid action!");
			}
			return redirect+UriComponentsBuilder.fromUriString("")
												.path(Url.adminPanel)
												.queryParam("code", code)
												.encode().build()
												.toUriString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return redirect+PrintError.toErrorPage(e);
		}
	}
	
	
	@PostMapping("/adminPanel/sUploadResume")
	public String sUploadResume(HttpServletRequest request)
	{
		DataAdminPanel dataAdminPanel = new DataAdminPanel();
		
		String fileName = request.getParameter("resumeDate");
		InputStream inStream;
		
		try
		{
			//Check 'resumeDate' input
			if (fileName==null || fileName.length()!=8 || fileName.contains("-") || fileName.contains("+"))
			{
				dataAdminPanel.setUploadResumeErr("Please enter the correct format, yyyyMMdd");
				throw new SomethingWentWrongException();
			}
			try
			{
				Integer.parseInt(fileName); //'resumeDate must be integer'
			}
			catch (NumberFormatException e)
			{
				dataAdminPanel.setUploadResumeErr("Integer ONLY!");
				throw e;
			}
			fileName = "resumeC_pdf_"+fileName; // Reuse variable to form a full filename
			// Check existence of the filename
			if (daoDl.doesExistFilename(fileName))
			{
				dataAdminPanel.setUploadResumeErr(fileName+" already exists");
				throw new SomethingWentWrongException();
			}
			Part fileData;
			//Use try-catch to check the file input
			try
			{
				fileData = request.getPart("resumeFile");
			}
			catch (IOException e)
			{
				dataAdminPanel.setUploadResumeErr("IOException!");
				throw new SomethingWentWrongException();
			}
			catch (IllegalStateException e)
			{
				dataAdminPanel.setUploadResumeErr("Size limit exceeded!");
				throw new SomethingWentWrongException();
			}
			catch (ServletException e)
			{
				dataAdminPanel.setUploadResumeErr("Incorrect data type!");
				throw new SomethingWentWrongException();
			}
			if (fileData==null)
			{
				dataAdminPanel.setUploadResumeErr("Please choose a file to upload");
				throw new SomethingWentWrongException();
			}
			//Convert the file from user input to our own Resource...
			Resource resource;
			try (BufferedInputStream buffInStream = new BufferedInputStream(fileData.getInputStream()))
			{
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				byte[] temp = new byte[4096];
				int length = 0;
				while ((length=buffInStream.read(temp))!=-1)
					bytes.write(temp, 0, length);
				resource = new ByteArrayResource(bytes.toByteArray());
			}
			catch (Exception e)
			{
				return redirect+PrintError.toErrorPage(e);
			}
			long size = resource.contentLength();
			if (size==0)
			{
				dataAdminPanel.setUploadResumeErr("File cannot be blank");
				throw new SomethingWentWrongException();
			}
			if (size>16000000)
			{
				dataAdminPanel.setUploadResumeErr("Must not exceed 15 MB");
				throw new SomethingWentWrongException();
			}
			inStream = resource.getInputStream();
			resource = null;
			System.gc();
		}
		catch (Exception e)
		{
			String code = sm.getSessionAdminPanel().generateCode(dataAdminPanel);
			return redirect+UriComponentsBuilder.fromUriString("")
												.path(Url.adminPanel)
												.queryParam("code", code)
												.encode().build().toUriString();
		}
		
		try (InputStream forAutoClose = inStream)
		{
			return redirect+sap.sUploadResume(fileName, inStream);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
	
	@PostMapping("/adminPanel/sSetResumeVersion")
	public String sSetResumeVersion(HttpServletRequest request)
	{
		DataAdminPanel dataAdminPanel = new DataAdminPanel();
		String fileName = request.getParameter("resumeName");
		try
		{
			if (fileName==null || !daoDl.doesExistFilename(fileName))
			{
				dataAdminPanel.setResumeVersionErr("Invalid resume version!");
				throw new SomethingWentWrongException();
			}
		}
		catch (Exception e)
		{
			String code = sm.getSessionAdminPanel().generateCode(dataAdminPanel);
			return redirect+UriComponentsBuilder.fromUriString("")
												.path(Url.adminPanel)
												.queryParam("code", code)
												.encode().build().toUriString();
		}
		try
		{
			return redirect+sap.sSetResumeVersion(fileName);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
	
	@PostMapping("/adminPanel/sSetAnnouncement")
	public String sSetAnnouncement(HttpServletRequest request)
	{
		DataAdminPanel dataAdminPanel = new DataAdminPanel();
		String announcement = request.getParameter("announcementMessage");
		try
		{
			if (announcement==null || announcement.length()<4 || announcement.length()>800)
			{
				dataAdminPanel.setAnnouncementErr("Must be between 4 - 800 characters!");
				throw new SomethingWentWrongException();
			}
		}
		catch (Exception e)
		{
			String code = sm.getSessionAdminPanel().generateCode(dataAdminPanel);
			return redirect+UriComponentsBuilder.fromUriString("")
												.path(Url.adminPanel)
												.queryParam("code", code)
												.encode().build().toUriString();
		}
		try
		{
			return redirect+sap.sSetAnnouncement(announcement);
		}
		catch (Exception e)
		{
			return redirect+PrintError.toErrorPage(e);
		}
	}
}
