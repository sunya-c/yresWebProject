package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	/**
	 * What this method does:<br>
	 * <br>
	 * 1. Gets fromPage value, which is the page the user clicked 'Log in'.<br>
	 * <br>
	 * 2. Validates username and password.<br>
	 * <br>
	 * 3. If BOTH username and password are valid, set session username to the
	 * logging in username and set session loggedIn to {@code true}. If username or
	 * password are invalid, set the err message to the session.
	 * 
	 * @param formL
	 * @return <strong>String of the page URL</strong> that the user clicked 'Log in'.
	 */
	public String sLogin(FormLogin formL)
	{
		String fromPage = sm.getSessionLogin().getFromPage();

		if (fromPage==null)
			fromPage = "Home";

		sm.clearLoginForm();
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
