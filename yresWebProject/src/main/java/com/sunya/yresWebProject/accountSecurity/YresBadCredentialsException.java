package com.sunya.yresWebProject.accountSecurity;

import org.springframework.security.authentication.BadCredentialsException;

public class YresBadCredentialsException extends BadCredentialsException
{
	private static final long serialVersionUID = 1L;

	public YresBadCredentialsException(String msg)
	{
		super(msg);
	}
}
