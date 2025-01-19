package com.sunya.managers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

public class CookieManager
{
	Cookie[] cookies;
	
	// Attribute names Feedback
	final public String CLIENT_REF = "YRES_clientRef_9123ks7df5ka4dif12339odsf";
	
	
	// Constructor
	public CookieManager(Cookie[] cookies)
	{
		this.cookies = cookies;
	}
	// end -- Constructor
	
	
	/**
	 * Remove <i>ERROR</i> attributes related to 
	 * {@code ServletLogin.java}, 
	 * which include:
	 * <p>    "fromPage",
	 * <p>    "wrongUsername",
	 * <p>    "wrongPassword"
	 */
	public String getCookieValue(String cookieName)
	{
		for (Cookie c : cookies)
		{
			if (c.getName().equals(cookieName))
				return c.getValue();
		}
		return null;
	}
	
	

}
