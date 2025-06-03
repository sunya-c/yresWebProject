package com.sunya.yresWebProject.accountSecurity.JWT;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.managers.CookieManager;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterJWT extends OncePerRequestFilter
{
	private SessionManager sm;
	private CookieManager cm;
	private ServiceJWT serJwt;
	private DaoLoginInfo dao;
	
	public FilterJWT(SessionManager sm, CookieManager cm, ServiceJWT serJwt, DaoLoginInfo dao)
	{
		this.sm = sm;
		this.cm = cm;
		this.serJwt = serJwt;
		this.dao = dao;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order 1.1, in Filter JWT ()");
		String bearer = request.getHeader("Authorization");
		String token;
		
		if (bearer!=null && bearer.startsWith("Bearer "))
			token = bearer.substring("Bearer ".length());
		else
			token = cm.getCookieValue(request.getCookies(), CookieManager.JWT_TOKEN);
		
		
		if (token==null || !serJwt.validateToken(token))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		ModelLoginInfo model = new ModelLoginInfo();
		try
		{
			model.setUsername(serJwt.getUsername(token));
		}
		catch (Exception e)
		{
			filterChain.doFilter(request, response);
			return;
		}
		if (!dao.doesExistUsernameCaseSen(model))
		{
			filterChain.doFilter(request, response);
			return;
		}
		String authority = (dao.isTempAccount(model.getUsername()))? "ROLE_USER" : "ROLE_ADMIN";
		synchronized (sm.getKeyHolder().getKeyLogin())
		{
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(new UsernamePasswordAuthenticationToken(
										model.getUsername(),
										null,
										Set.of(new SimpleGrantedAuthority(authority))));
		}
		if (serJwt.almostExpire(token))
		{
			response.addCookie(cm.createJWTCookie(model.getUsername()));
		}
		sm.clearLoginForm();
		filterChain.doFilter(request, response);
		return;
	}
}
