package com.sunya.managers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

public class CookieManager
{
	private Cookie[] cookies;
	
	// Attribute names Feedback
	final public String CLIENT_REF = "YRES_clientRef_9123ks7df5ka4dif12339odsf";
	
	
	// Constructor
	public CookieManager(Cookie[] cookies)
	{
		this.cookies = cookies;
	}
	// end -- Constructor
	
	
	/**
	 * Get the <strong>value</strong> of the specified cookie name.
	 * 
	 * @param cookieName ~ the name of the interested cookie.
	 * @return <strong>String cookieValue</strong> ~ the value of the specified cookie.<br>
	 *         <strong>null</strong> ~ if either the cookie name doesn't exist or the cookie contains nothing.
	 */
	public String getCookieValue(String cookieName)
	{
		if (cookies != null)
		{
			for (Cookie c : cookies)
			{
				if (c.getName().equals(cookieName))
					return c.getValue();
			}
		}
		return null;
	}
	
	

}
