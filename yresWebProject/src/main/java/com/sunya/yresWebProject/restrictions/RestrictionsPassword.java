package com.sunya.yresWebProject.restrictions;

import com.sunya.yresWebProject.models.FormPassword;

public abstract class RestrictionsPassword
{
	protected boolean confirmPassword(FormPassword formCA)
	{
		return (formCA.getPassword1().equals(formCA.getPassword2()));
	}
	
	protected boolean checkLengthPassword(FormPassword formCA)
	{
		return (formCA.getPassword1().length()>=4 && formCA.getPassword1().length()<=20);
	}
	
	protected boolean checkSpacePassword(FormPassword formCA)
	{
		return !(formCA.getPassword1().contains(" "));
	}
}
