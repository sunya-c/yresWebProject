package com.sunya;

import com.sunya.daos.DaoLoginInfo;

import jakarta.servlet.ServletException;

public class RestrictionsCreateAccount
{
	DaoLoginInfo dao;
	ErrorMessageSetterCreateAccount errSetter;
	String username;
	String password1;
	String password2;

	// Constructor
	public RestrictionsCreateAccount(DaoLoginInfo dao, ErrorMessageSetterCreateAccount errSetter, String username, String password1, String password2)
	{
		this.errSetter = errSetter;
		this.username = username;
		this.password1 = password1;
		this.password2 = password2;
		this.dao = dao;
	}
	// end -- Constructor

	
	public boolean checkRestriction() throws ServletException
	{
		boolean validInfo = true;
		
		// username check
		if (checkLengthUsername())
		{
			if (checkSpaceUsername())
			{
				if (dao.checkUsername(username))
				{
					errSetter.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_DUPLICATE);  // pass error message enum here.
					validInfo = false;
				}
			}
			else
			{
				errSetter.setUsernameErr(ErrMsg.CREATEACCOUNT_UNAME_SPACE);  // pass error message enum here.
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
				errSetter.setPasswordErr1(ErrMsg.CREATEACCOUNT_PASS_SPACE);  // pass error message here.
				validInfo = false;
			}
		}
		else
		{
			errSetter.setPasswordErr1(ErrMsg.CREATEACCOUNT_PASS_LENGTH);  // pass error message enum here.
			validInfo = false;
		}
		
		// confirm password check
		if (!confirmPassword())
		{
			errSetter.setPasswordErr2(ErrMsg.CREATEACCOUNT_PASS_CONFIRM);  // pass error message enum here.
			validInfo = false;
		}
		
		// overall check
		return validInfo;
	}
	
	
	
	private boolean confirmPassword()
	{
		if (password1 == null || password2 == null)
			return false;
		else
			return (password1.equals(password2));
	}

	private boolean checkLengthUsername()
	{
		if (username == null)
			return false;
		else
			return (username.length() >= 4 && username.length() <= 10);
	}
	
	private boolean checkLengthPassword()
	{
		if (password1 == null)
			return false;
		else
			return (password1.length() >= 4 && password1.length() <= 20);
	}

	private boolean checkSpaceUsername()
	{
		if (username == null)
			return false;
		else
			return !(username.contains(" "));
	}
	
	private boolean checkSpacePassword()
	{
		if (password1 == null)
			return false;
		else
			return !(password1.contains(" "));
	}
}
