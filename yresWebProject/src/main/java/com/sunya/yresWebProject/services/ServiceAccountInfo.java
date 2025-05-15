package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataAccountInfo;
import com.sunya.yresWebProject.models.FormChangePassword;
import com.sunya.yresWebProject.models.ModelLoginInfo;
import com.sunya.yresWebProject.restrictions.RestrictionsChangePassword;

@Service
public class ServiceAccountInfo
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private RestrictionsChangePassword restriction;
	
	
	public String sChangePassword(FormChangePassword formCP)
	{
		ModelLoginInfo model = new ModelLoginInfo();
		model.setUsername(sm.getSessionLogin().getUsernameUnescaped());
		model.setPassword(formCP.getCurrentPassword());
		DataAccountInfo dataAccountInfo = new DataAccountInfo();
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			try
			{
				boolean throwException = false;
				if (!dao.doesExistPasswordCaseSen(model))
				{
					dataAccountInfo.setCurrentPasswordErr("Incorrect password!");
					throwException = true;
				}
				if (!restriction.checkRestriction(formCP, dataAccountInfo))
				{
					throwException = true;
				}
				if (throwException)
				{
					throw new SomethingWentWrongException();
				}
			}
			catch (SomethingWentWrongException e)
			{
				String codeAccInfo = sm.getSessionAccountInfo().generateCode(dataAccountInfo);
				return UriComponentsBuilder.fromUriString("")
											.path(Url.accountInfo)
											.queryParam("code", codeAccInfo)
											.encode().build().toUriString();
			}
			model.setPassword(formCP.getPassword1());
			dao.changePassword(model);
		}
		String codeRedi = sm.getSessionRedirecting().generateCode();
//		return UriComponentsBuilder.fromUriString("")
//							.path(Url.redirecting)
//							.queryParam("code", codeRedi)
//							.encode().build().toUriString();
		return UriComponentsBuilder.fromUriString("")
									.path(Url.redirecting)
									.queryParam("message", "Done!")
									.queryParam("destinationPage", "Home")
									.queryParam("destinationUrl", "/welcome")
									.queryParam("code", codeRedi)
									.encode().build().toUriString();
	}
}
