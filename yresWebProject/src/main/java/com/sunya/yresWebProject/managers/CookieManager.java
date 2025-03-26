package com.sunya.yresWebProject.managers;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;

@Component
public class CookieManager
{
	public static final String CLIENT_REF = "YRES_clientRef_9123ks7df5ka4dif12339odsf";
	
	
	
	/**
	 * Get the <strong>value</strong> of the given cookieName.
	 * 
	 * @param cookies ~ Get this by {@code request.getCookies()}.
	 * @param cookieName ~ the name of the interested cookie.
	 * @return <strong>String of cookieValue</strong> ~ the value of the specified cookie.<br>
	 *         <strong>null</strong> ~ if either the cookie name doesn't exist or the cookie contains nothing.
	 */
	public String getCookieValue(Cookie[] cookies, String cookieName)
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
