package com.sunya.yresWebProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.filters.FilterAccountExistence;
import com.sunya.yresWebProject.filters.FilterBot;
import com.sunya.yresWebProject.filters.FilterInitializeSession;
import com.sunya.yresWebProject.filters.FilterLoginState;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage0;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage1;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage2;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage3;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage4;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage5;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage6;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage7;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage8;
import com.sunya.yresWebProject.managers.SessionManager;

@Configuration
public class FilterConfig
{
	@Autowired
	private SessionManager sm;
	
	
	@Bean
	public FilterRegistrationBean<FilterBot> filterBot(DaoIPBlacklist dao)
	{
		FilterRegistrationBean<FilterBot> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterBot(dao));
		bean.addUrlPatterns("/Home");
		bean.setOrder(0);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterInitializeSession> filterInitializeSession()
	{
		FilterRegistrationBean<FilterInitializeSession> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterInitializeSession(sm));
		bean.addUrlPatterns(
							"/createAccount",
							"/yresError",
							"/feedback",
							"/Home",
							"/personalInformation",
							"/redirecting",
//							"/UnderConstructionPage.jsp",
							"/welcome",
							"/sCreateAccount",
							"/sDownloadResume",
							"/sFeedback",
							"/sLogin",
							"/sLogout");
		bean.setOrder(1);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterAccountExistence> filterAccExistence(DaoLoginInfo dao)
	{
		FilterRegistrationBean<FilterAccountExistence> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterAccountExistence(sm, dao));
		bean.addUrlPatterns(
							"/createAccount",
							"/yresError",
							"/feedback",
							"/Home",
							"/personalInformation",
							"/redirecting",
//							"/UnderConstructionPage.jsp",
							"/welcome",
							"/sCreateAccount",
							"/sDownloadResume",
							"/sFeedback",
							"/sLogin",
							"/sLogout");
		bean.setOrder(2);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterLoginState> filterLoginState()
	{
		FilterRegistrationBean<FilterLoginState> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterLoginState(sm));
		bean.addUrlPatterns("/welcome");
		bean.setOrder(3);
		
		return bean;
	}
	
	
	
	@Autowired
	private FilterSiteUsage siteUsage;
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage0> filterUsage0()  // Filter CreateAccount
	{
		FilterRegistrationBean<FilterSiteUsage0> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage0(siteUsage));
		bean.addUrlPatterns("/createAccount");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage1> filterUsage1()  // Filter ErrorPage
	{
		FilterRegistrationBean<FilterSiteUsage1> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage1(siteUsage));
		bean.addUrlPatterns("/yresError");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage2> filterUsage2()  // Filter FeedbackPage
	{
		FilterRegistrationBean<FilterSiteUsage2> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage2(siteUsage));
		bean.addUrlPatterns("/feedback");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage3> filterUsage3()  // Filter Home/Login
	{
		FilterRegistrationBean<FilterSiteUsage3> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage3(siteUsage));
		bean.addUrlPatterns("/Home");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage4> filterUsage4()  // Filter PersInfoPage
	{
		FilterRegistrationBean<FilterSiteUsage4> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage4(siteUsage));
		bean.addUrlPatterns("/personalInformation");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage5> filterUsage5()  // Filter RedirectingPage
	{
		FilterRegistrationBean<FilterSiteUsage5> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage5(siteUsage));
		bean.addUrlPatterns("/redirecting");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage6> filterUsage6()  // Filter UnderConstruction
	{
		FilterRegistrationBean<FilterSiteUsage6> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage6(siteUsage));
//		bean.addUrlPatterns("/~~~");
		bean.setOrder(4);
		bean.setEnabled(false);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage7> filterUsage7()  // Filter WelcomePage
	{
		FilterRegistrationBean<FilterSiteUsage7> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage7(siteUsage));
		bean.addUrlPatterns("/welcome");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage8> filterUsage8()  // Filter sDownloadResume
	{
		FilterRegistrationBean<FilterSiteUsage8> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage8(siteUsage));
		bean.addUrlPatterns("/sDownloadResume");
		bean.setOrder(4);
		
		return bean;
	}
}
