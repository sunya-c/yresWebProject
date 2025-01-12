package com.sunya;

import com.sunya.daos.DaoLoginInfo;

import jakarta.servlet.ServletException;

public class RestrictionsCreateAccount
{
	DaoLoginInfo dao;
	ErrorMessageSetterCreateAccount errMessage;
	String username;
	String password1;
	String password2;

	public RestrictionsCreateAccount(DaoLoginInfo dao, ErrorMessageSetterCreateAccount errMessage, String username, String password1, String password2)
	{
		this.errMessage = errMessage;
		this.username = username;
		this.password1 = password1;
		this.password2 = password2;
		this.dao = dao;
	}

	
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
					errMessage.setUsernameErr(ErrMsg.DUPLICATE_UNAME_ERR);  // pass error message enum here.
					validInfo = false;
				}
			}
			else
			{
				errMessage.setUsernameErr(ErrMsg.SPACE_UNAME_ERR);  // pass error message enum here.
				validInfo = false;
			}
		}
		else
		{
			errMessage.setUsernameErr(ErrMsg.LENGTH_UNAME_ERR);
			validInfo = false;
		}
		
		// password check
		if (checkLengthPassword())
		{
			if (!checkSpacePassword())
			{
				errMessage.setPasswordErr1(ErrMsg.SPACE_PASS_ERR);  // pass error message here.
				validInfo = false;
			}
		}
		else
		{
			errMessage.setPasswordErr1(ErrMsg.LENGTH_PASS_ERR);  // pass error message enum here.
			validInfo = false;
		}
		
		// confirm password check
		if (!confirmPassword())
		{
			errMessage.setPasswordErr2(ErrMsg.CONFIRM_PASS_ERR);  // pass error message enum here.
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
