package com.sunya.yresWebProject.accountSecurity;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.accountSecurity.JWT.FilterJWT;
import com.sunya.yresWebProject.accountSecurity.JWT.ServiceJWT;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.filters.FilterBot;
import com.sunya.yresWebProject.filters.FilterHttps;
import com.sunya.yresWebProject.filters.FilterInitializeSession;
import com.sunya.yresWebProject.managers.CookieManager;
import com.sunya.yresWebProject.managers.SessionManager;

import io.ipinfo.api.IPinfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AccountSecurityConfig
{
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager getAuthManager(AuthenticationConfiguration authConfig) throws Exception
	{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public RoleHierarchy getRoleHierarchy()
	{
		return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
	}
	
	
	@Bean
	public SecurityFilterChain getSecurityFilterChain(
								HttpSecurity http,
								AuthenticationManager authManager,
								SessionManager sm,
								DaoIPBlacklist daoBl,
								IPinfo ipinfo,
								Environment env,
								CookieManager cm,
								ServiceJWT serJwt,
								DaoLoginInfo daoLg,
								UserAuthContext userAuth) throws Exception
	{
		System.err.println("create SecurityFilterChain");
		
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("https://"+env.getProperty("yres.domain", "YresEnvNotFound"));
		corsConfig.addAllowedMethod(CorsConfiguration.ALL);
		UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
		corsSource.registerCorsConfiguration("/**", corsConfig);
		http.cors(customCors -> customCors.configurationSource(corsSource));
		
		http.csrf(csrf ->
			{
				csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
				csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository());
			});
		
		http.sessionManagement(session ->
			{
				session.maximumSessions(1);
				session.sessionCreationPolicy(SessionCreationPolicy.NEVER);
			}
		);
		http.authorizeHttpRequests(auth -> auth.requestMatchers(
																"/saveBotstodatabase",
																"/adminPanel",
																"/adminPanel/**").hasRole("ADMIN")
												.requestMatchers(
//																"/welcome",
//																"/welcome/**",
																"/accountInfo",
																"/accountInfo/**").hasRole("USER")
												.requestMatchers("/**").permitAll())
			.exceptionHandling(fail -> fail.accessDeniedHandler(new CustomAccessDeniedHandler()))
			.formLogin(login ->
				{
					login.usernameParameter("username")
						.passwordParameter("password")
						.loginPage("/login")
						.loginProcessingUrl("/sLogin")
						.successHandler(new CustomSuccessHandler(sm, cm))
						.failureHandler(new CustomFailureHandler(sm))
						.permitAll();
				}
			)
			.logout(logout -> logout.deleteCookies(CookieManager.JWT_TOKEN)
				 					.invalidateHttpSession(false)
				 					.logoutUrl("/sLogout")
				 					.clearAuthentication(true)
				 					.logoutSuccessHandler(new CustomLogoutSuccessHandler(sm)))
			.addFilterBefore(new FilterJWT(sm, cm, serJwt, daoLg, userAuth), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new FilterInitializeSession(sm), FilterJWT.class)
			.addFilterBefore(new FilterBot(daoBl, ipinfo), FilterInitializeSession.class)
			.addFilterBefore(new FilterHttps(env), FilterBot.class);
		CustomBasicAuthFilter basicCustomFilter = new CustomBasicAuthFilter("/basicLogin", authManager, sm);
		basicCustomFilter.setAuthenticationFailureHandler((request, response, exception) -> 
		{
			System.err.println("in Bad cred handler!");
			response.sendRedirect("/badCredentials");
		});
		basicCustomFilter.setAuthenticationSuccessHandler(new CustomSuccessHandler(sm, cm));
		http.addFilterAfter(basicCustomFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	public class CustomSuccessHandler implements AuthenticationSuccessHandler
	{
		private SessionManager sm;
		private CookieManager cm;
		
		public CustomSuccessHandler(SessionManager sm, CookieManager cm)
		{
			this.sm = sm;
			this.cm = cm;
		}

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException
		{
			sm.clearLoginForm();
			response.addCookie(cm.createJWTCookie(authentication.getName()));
			response.sendRedirect("/"+sm.getSessionLogin().getFromPage());
		}
	}
	
	public class CustomFailureHandler implements AuthenticationFailureHandler
	{
		private SessionManager sm;
		
		public CustomFailureHandler(SessionManager sm)
		{
			this.sm = sm;
		}

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
									AuthenticationException exception) throws IOException, ServletException
		{
			if (exception instanceof BadCredentialsException && sm.getSessionLogin().getUsernameErr()==null)
				sm.getSessionLogin().setPasswordErr("Incorrect password!");
			response.sendRedirect("/"+Url.login);
		}
	}
	
	public class CustomAccessDeniedHandler implements AccessDeniedHandler
	{
		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
									AccessDeniedException accessDeniedException) throws IOException, ServletException
		{
			PrintError.toErrorPage(response, new SomethingWentWrongException("You don't have permission to access this page"));
		}
	}
	
	public class CustomLogoutSuccessHandler implements LogoutSuccessHandler
	{
		private SessionManager sm;
		
		public CustomLogoutSuccessHandler(SessionManager sm)
		{
			this.sm = sm;
		}
		
		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException
		{
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				sm.clearLoginForm();
				response.sendRedirect("/"+sm.getSessionLogin().getFromPage());
			}
		}
	}
}
















