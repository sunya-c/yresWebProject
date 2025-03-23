package com.sunya.yresWebProject.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.FormLogin;
import com.sunya.yresWebProject.models.ModelLoginInfo;

@Service
public class ServiceLogin
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	
	
	
	public String sLogin(FormLogin formL) throws SQLException
	{
		String fromPage = sm.getSessionLogin().getFromPage();
		
		if (fromPage == null)
			fromPage = "Home";
		
		sm.removeLoginErr();
		sm.getSessionLogin().setUsernamePreTyped(formL.getUsername());


		ModelLoginInfo model = new ModelLoginInfo();
		model.setUsername(formL.getUsername());
		model.setPassword(formL.getPassword());
		
		
		if (!dao.checkUsernameCaseSen(model)) // if username is invalid
		{
			sm.getSessionLogin().setUsernameErr("Invalid username!");
			
			return fromPage;
		}
		else // if username is valid, proceed with password check
		{
			if (!dao.checkPasswordCaseSen(model))
			{
				sm.getSessionLogin().setPasswordErr("Incorrect password!");
				
				return fromPage;
			}
			else
			{
				sm.getSessionLogin().setUsernamePreTyped(null);
				sm.getSessionLogin().setUsername(formL.getUsername());
				sm.getSessionLogin().setLoggedIn(true);
				
				return fromPage;
			}
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}
