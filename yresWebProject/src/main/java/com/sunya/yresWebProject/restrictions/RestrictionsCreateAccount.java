package com.sunya.yresWebProject.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.models.ModelLoginInfo;

/**
 * Call {@code setupRestrictionCreateAccount(username, password1, password2)}
 * first before {@code checkRestriction()}
 */
@Component
@Scope("prototype")
public class RestrictionsCreateAccount
{
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private ErrorMessageSetterCreateAccount errSetter;
	FormCreateAccount formCA;
	
	
	
	private void setupRestrictionCreateAccount(FormCreateAccount formCA)
	{
		this.formCA = formCA;
	}



	/**
	 * Check whether the given username and password comply with the restriction.
	 * Set up the username, password1, and password2 before calling this method.
	 * 
	 * @return <strong>true</strong> ~ if the given username and password comply
	 *         with ALL the restriction.<br>
	 *         <strong>false</strong> ~ if at least one of them doesn't meet the
	 *         restriction. The error messages, containing the detail, will be set
	 *         to the session by {@code ErrorMessageSetterCreateAccount.java} object
	 */
	public boolean checkRestriction(FormCreateAccount formCA)
	{
		setupRestrictionCreateAccount(formCA);
		
		boolean validInfo = true;

		// username check
		if (checkLengthUsername())
		{
			if (checkSpaceUsername())
			{
				ModelLoginInfo model = new ModelLoginInfo();
				model.setUsername(formCA.getUsername());
				
				if (dao.checkUsername(model))
				{
					errSetter.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_DUPLICATE); // pass error message enum here.
					validInfo = false;
				}
			}
			else
			{
				errSetter.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_SPACE); // pass error message enum here.
				validInfo = false;
			}
		}
		else
		{
			errSetter.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_LENGTH);
			validInfo = false;
		}

		// password check
		if (checkLengthPassword())
		{
			if (!checkSpacePassword())
			{
				errSetter.setPasswordErr1(ErrMsg.CREATEACCOUNT_PASS_SPACE); // pass error message here.
				validInfo = false;
			}
		}
		else
		{
			errSetter.setPasswordErr1(ErrMsg.CREATEACCOUNT_PASS_LENGTH); // pass error message enum here.
			validInfo = false;
		}

		// confirm password check
		if (!confirmPassword())
		{
			errSetter.setPasswordErr2(ErrMsg.CREATEACCOUNT_PASS_CONFIRM); // pass error message enum here.
			validInfo = false;
		}
		
		// overall check result
		return validInfo;
	}



	private boolean confirmPassword()
	{
		return (formCA.getPassword1().equals(formCA.getPassword2()));
	}



	private boolean checkLengthUsername()
	{
		return (formCA.getUsername().length()>=4 && formCA.getUsername().length()<=10);
	}



	private boolean checkLengthPassword()
	{
		return (formCA.getPassword1().length()>=4 && formCA.getPassword1().length()<=20);
	}



	private boolean checkSpaceUsername()
	{
		return !(formCA.getUsername().contains(" "));
	}



	private boolean checkSpacePassword()
	{
		return !(formCA.getPassword1().contains(" "));
	}
}
