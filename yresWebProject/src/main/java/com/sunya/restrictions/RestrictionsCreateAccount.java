package com.sunya.restrictions;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.daos.DaoLoginInfo;

import jakarta.servlet.ServletException;

/**
 * Call {@code setupRestrictionCreateAccount(username, password1, password2)} first before {@code checkRestriction()}
 */
@Component
public class RestrictionsCreateAccount
{
	@Autowired
	private DaoLoginInfo dao;
	@Autowired
	private ErrorMessageSetterCreateAccount errSetter;
	private String username;
	private String password1;
	private String password2;
	
	
	
	
	public void setupRestrictionCreateAccount(String username, String password1, String password2)
	{
		this.username = username;
		this.password1 = password1;
		this.password2 = password2;
	}
	
	
	
	
	/**
	 * Check whether the given username and password comply with the restriction. 
	 * Set up the username, password1, and password2 before calling this method.
	 * 
	 * @return <strong>true</strong> ~ if the given username and password comply with ALL the restriction.<br>
	 *         <strong>false</strong> ~ if at least one of them doesn't meet the restriction. 
	 *         							The error messages, containing the detail, will be set to the session by {@code ErrorMessageSetterCreateAccount.java} object
	 * @throws SQLException 
	 * @throws ServletException
	 */
	public boolean checkRestriction() throws SQLException
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
		
		clearVariables();
		
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
	
	private void clearVariables()
	{
		username = null;
		password1 = null;
		password2 = null;
	}
}
