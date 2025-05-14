package com.sunya.yresWebProject.services;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoDeleteBlacklistedUsage;
import com.sunya.yresWebProject.daos.DaoDownloadinfo;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.daos.DaoWebdatainfo;
import com.sunya.yresWebProject.filters.FilterBot;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataAdminPanel;
import com.sunya.yresWebProject.models.ModelWebdatainfo;
import com.sunya.yresWebProject.rest.repositories.models.ModelIPBlacklist;

@Service
public class ServiceAdminPanel
{
	@Autowired
	FilterRegistrationBean<FilterBot> filterBot;
	@Autowired
	DaoIPBlacklist daoIpbl;
	@Autowired
	DaoDeleteBlacklistedUsage daoDelusg;
	@Autowired
	DaoWebdatainfo daoWb;
	@Autowired
	DaoDownloadinfo daoDl;
	@Autowired
	SessionManager sm;
	@Autowired
	@Qualifier("noSeparatorBackDate")
	DateTimeFormatter dateFormatNoSep;
	@Autowired
	@Qualifier("noSeparatorBackDateTime")
	DateTimeFormatter dateTimeFormatNoSep;
	
	
	public void sActionSaveBotstodatabase(DataAdminPanel dataAdminPanel)
	{
		FilterBot.CustomArrayList tempBlacklist;
		FilterBot.CustomArrayList cachedBlacklist;
		ArrayList<ModelIPBlacklist> existing = new ArrayList<>();
		ArrayList<ModelIPBlacklist> newlyAdded = new ArrayList<>();
		ArrayList<ModelIPBlacklist> cachedAdded = new ArrayList<>();

		tempBlacklist = filterBot.getFilter().getTempBlacklist();
		cachedBlacklist = filterBot.getFilter().getCachedBlacklist();
		synchronized (tempBlacklist.getKeySynchronized())
		{
			tempBlacklist.stream().forEach(model -> {
				if (daoIpbl.isBlacklisted(model.getIpAddress()))
				{
					daoIpbl.incrementCounter(model.getIpAddress(), model.getBlockCount());
					existing.add(model);
				}
				else
				{
					daoIpbl.addToBlacklist(model.getIpAddress(), model.getCountryCode(), model.getBlockCount());
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
					daoIpbl.incrementCounter(model.getIpAddress(), model.getBlockCount());
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
		
		dataAdminPanel.setActionResults("existing items: "+existing.size()+"<br>"
									+ "new items: "+newlyAdded.size()+"<br>"
									+ "cached items: "+cachedAdded.size()+".");
	}
	
	
	public void sActionDeleteBotsinusageinfo(DataAdminPanel dataAdminPanel)
	{
		int total = daoDelusg.removeBlacklistedFromUsageinfo();
		dataAdminPanel.setActionResults("rows deleted: "+total+".");
	}
	
	public String sUploadResume(String filename, InputStream inStream)
	{
		daoDl.addFile(filename, inStream);
		String code = sm.getSessionRedirecting().generateCode();
		return UriComponentsBuilder.fromUriString("")
									.path(Url.redirecting)
									.queryParam("message", "File uploaded!")
									.queryParam("destinationPage", "Admin Panel page")
									.queryParam("destinationUrl", "/adminPanel")
									.queryParam("code", code)
									.encode().build().toUriString();
	}
	
	public String sSetResumeVersion(String fileName)
	{
		LocalDate resumeDate = LocalDate.parse(fileName.substring("resumeC_pdf_".length()), dateFormatNoSep);
		daoWb.setResumeDate(resumeDate);
		String code = sm.getSessionRedirecting().generateCode();
		return UriComponentsBuilder.fromUriString("")
									.path(Url.redirecting)
									.queryParam("message", "Resume date updated!")
									.queryParam("destinationPage", "Admin Panel page")
									.queryParam("destinationUrl", "/adminPanel")
									.queryParam("code", code)
									.encode().build().toUriString();
	}
	
	public String sSetAnnouncement(String announcement)
	{
		ModelWebdatainfo model = daoWb.getWebinfo(DaoWebdatainfo.WEB_NOTE1);
		if (model!=null)
		{
			String keyName = DaoWebdatainfo.WEB_NOTE1+"_"+LocalDateTime.now().format(dateTimeFormatNoSep);
			daoWb.addWebinfo(keyName, model.getValue());
		}
		daoWb.updateWebinfo(DaoWebdatainfo.WEB_NOTE1, announcement);
		
		String code = sm.getSessionRedirecting().generateCode();
		return UriComponentsBuilder.fromUriString("")
									.path(Url.redirecting)
									.queryParam("message", "Announcement updated!")
									.queryParam("destinationPage", "Admin Panel page")
									.queryParam("destinationUrl", "/adminPanel")
									.queryParam("code", code)
									.encode().build().toUriString();
	}
}
