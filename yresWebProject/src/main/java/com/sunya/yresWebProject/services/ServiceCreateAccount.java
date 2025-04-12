package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataCreateAccount;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.models.ModelLoginInfo;
import com.sunya.yresWebProject.restrictions.ErrMsg;
import com.sunya.yresWebProject.restrictions.RestrictionsCreateAccount;

@Service
public class ServiceCreateAccount
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private RestrictionsCreateAccount restriction;


	/**
	 * What this method does:<br>
	 * <br>
	 * 1. Validate the data from <strong>CreateAccount form</strong>.<br>
	 * <br>
	 * 2. If the data is valid, add the data to <strong>logininfo</strong> table
	 * (create new account).<br>
	 * <br>
	 * 3. If data is added successfully, generate a code for accessing
	 * <strong>Redirecting page</strong>. This code will be appended to the end of
	 * the URL.
	 * 
	 * @param formCA
	 * @param dataCreateAccount
	 * @param codeCreateAccount
	 * @return <strong>String of Redirecting page URL</strong> ~ if data is
	 *         added successfully.<br>
	 *         <strong>String of CreateAccount page URL</strong> ~ if
	 *         otherwise.
	 */
	public String sCreateAccount(FormCreateAccount formCA, DataCreateAccount dataCreateAccount,
								String codeCreateAccount)
	{
		dataCreateAccount.setUsernamePreTyped(formCA.getUsername());

		if (restriction.checkRestriction(formCA, dataCreateAccount))
		{
			ModelLoginInfo model = new ModelLoginInfo();
			model.setUsername(formCA.getUsername());
			model.setPassword(formCA.getPassword1());

			try
			{
				dao.addUser(model);
			}
			catch (SomethingWentWrongException e)
			{
				ErrMsg errMessage = ErrMsg.CREATEACCOUNT_UNAME_LENGTH;
				errMessage.setCustomErrMessage("Something's wrong, pls try again.");
				dataCreateAccount.setUsernameErr(errMessage.toString());
				return Url.createAccount+"?code="+codeCreateAccount;
			}
			sm.getSessionLogin().setUsernamePreTyped(formCA.getUsername());

			String codeRedirecting = sm.getSessionRedirecting().generateCode();

			return Url.redirecting+"?message=Done!&destinationPage=Home&destinationUrl=Home&code="+codeRedirecting;
		}
		else
		{
			return Url.createAccount+"?code="+codeCreateAccount;
		}
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
}
