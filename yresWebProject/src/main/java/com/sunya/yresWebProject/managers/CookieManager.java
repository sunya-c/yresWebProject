package com.sunya.yresWebProject.managers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.accountSecurity.JWT.ServiceJWT;

import jakarta.servlet.http.Cookie;

@Component
public class CookieManager
{
	public static final String JSESSION = "JSESSIONID";
	public static final String CLIENT_REF = "YRES_clientRef_9123ks7df5ka4dif12339odsf";
	public static final String JWT_TOKEN = "YRES_token";
	
	@Autowired
	private ServiceJWT serJwt;
	
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
	
	public Cookie createCookie(String name, String value, int ageInSeconds)
	{
		Cookie c = new Cookie(name, value);
		c.setMaxAge(ageInSeconds);
		c.setPath("/");
		c.setSecure(true);
		c.setHttpOnly(true);
		c.setAttribute("SameSite", "Lax");
		return c;
	}
	
	public Cookie createJWTCookie(String username)
	{
		return createCookie(
								CookieManager.JWT_TOKEN,
								serJwt.generateToken(username),
								(int)Duration.ofMinutes(ServiceJWT.tokenAge).getSeconds());
	}
}
