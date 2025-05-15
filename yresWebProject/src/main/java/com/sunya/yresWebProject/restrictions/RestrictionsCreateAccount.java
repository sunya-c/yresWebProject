package com.sunya.yresWebProject.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.models.DataCreateAccount;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.models.ModelLoginInfo;

@Component
public class RestrictionsCreateAccount extends RestrictionsPassword
{
	@Autowired
	private DaoLoginInfo dao;


	/**
	 * Check whether the given username and password comply with the restrictions.
	 * 
	 * @param formCA
	 * @param dataCreateAccount
	 * @return <strong>true</strong> ~ if the given username and password comply
	 *         with ALL restrictions.<br>
	 *         <strong>false</strong> ~ if at least one of them doesn't meet the
	 *         restriction. The err message will be set to {@code DataCreateAccount}
	 *         object.
	 */
	public boolean checkRestriction(FormCreateAccount formCA, DataCreateAccount dataCreateAccount)
	{
		boolean validInfo = true;

		// username check
		if (checkLengthUsername(formCA))
		{
			if (checkSpaceUsername(formCA))
			{
				ModelLoginInfo model = new ModelLoginInfo();
				model.setUsername(formCA.getUsername());

				if (dao.checkUsername(model))
				{
					dataCreateAccount.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_DUPLICATE.toString()); // pass error
																										// message enum
																										// here.
					validInfo = false;
				}
			}
			else
			{
				dataCreateAccount.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_SPACE.toString()); // pass error message
																								// enum here.
				validInfo = false;
			}
		}
		else
		{
			dataCreateAccount.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_LENGTH.toString());
			validInfo = false;
		}

		// password check
		if (checkLengthPassword(formCA))
		{
			if (!checkSpacePassword(formCA))
			{
				dataCreateAccount.setPassword1Err(ErrMsg.CREATEACCOUNT_PASS_SPACE.toString()); // pass error message
																								// here.
				validInfo = false;
			}
		}
		else
		{
			dataCreateAccount.setPassword1Err(ErrMsg.CREATEACCOUNT_PASS_LENGTH.toString()); // pass error message enum
																							// here.
			validInfo = false;
		}

		// confirm password check
		if (!confirmPassword(formCA))
		{
			dataCreateAccount.setPassword2Err(ErrMsg.CREATEACCOUNT_PASS_CONFIRM.toString());// pass error message enum
																							// here.
			validInfo = false;
		}

		// overall check result
		return validInfo;
	}
	
	private boolean checkLengthUsername(FormCreateAccount formCA)
	{
		return (formCA.getUsername().length()>=4 && formCA.getUsername().length()<=10);
	}
	private boolean checkSpaceUsername(FormCreateAccount formCA)
	{
		return !(formCA.getUsername().contains(" "));
	}
}
