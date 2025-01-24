package com.sunya.exceptions;

public class SuspiciousRequestException extends Exception
{

	public SuspiciousRequestException()
	{
		super();
	}

	public SuspiciousRequestException(String message)
	{
		super(message);
	}
}
