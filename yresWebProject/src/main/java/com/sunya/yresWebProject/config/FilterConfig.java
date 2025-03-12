package com.sunya.yresWebProject.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sunya.yresWebProject.filters.FilterAccountExistence;
import com.sunya.yresWebProject.filters.FilterBot;
import com.sunya.yresWebProject.filters.FilterLoginState;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage0;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage1;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage2;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage3;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage4;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage5;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage6;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage7;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage8;

@Configuration
public class FilterConfig
{
	@Bean
	public FilterRegistrationBean<FilterBot> filterBot()
	{
		FilterRegistrationBean<FilterBot> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterBot());
		bean.addUrlPatterns("/Home");
		bean.setOrder(1);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterAccountExistence> filterAccExistence()
	{
		FilterRegistrationBean<FilterAccountExistence> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterAccountExistence());
		bean.addUrlPatterns(
				"/createAccount",
				"/error",
				"/feedback",
				"/Home",
				"/personalInformation",
				"/redirecting",
//				"/UnderConstructionPage.jsp",
				"/welcome",
				"/sCreateAccount",
				"/sDownloadResume",
				"/sFeedback",
				"/sLogin",
				"/sLogout",
				"/sPersonalInformation");
		bean.setOrder(2);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterLoginState> filterLoginState()
	{
		FilterRegistrationBean<FilterLoginState> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterLoginState());
		bean.addUrlPatterns("/welcome");
		bean.setOrder(3);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage0> filterUsage0()  // Filter CreateAccount
	{
		FilterRegistrationBean<FilterSiteUsage0> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage0());
		bean.addUrlPatterns("/createAccount");
		bean.setOrder(4);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage1> filterUsage1()  // Filter ErrorPage
	{
		FilterRegistrationBean<FilterSiteUsage1> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage1());
		bean.addUrlPatterns("/error");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage2> filterUsage2()  // Filter FeedbackPage
	{
		FilterRegistrationBean<FilterSiteUsage2> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage2());
		bean.addUrlPatterns("/feedback");
		bean.setOrder(6);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage3> filterUsage3()  // Filter Home/Login
	{
		FilterRegistrationBean<FilterSiteUsage3> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage3());
		bean.addUrlPatterns("/Home");
		bean.setOrder(7);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage4> filterUsage4()  // Filter PersInfoPage
	{
		FilterRegistrationBean<FilterSiteUsage4> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage4());
		bean.addUrlPatterns("/personalInformation");
		bean.setOrder(8);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage5> filterUsage5()  // Filter RedirectingPage
	{
		FilterRegistrationBean<FilterSiteUsage5> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage5());
		bean.addUrlPatterns("/redirecting");
		bean.setOrder(9);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage6> filterUsage6()  // Filter UnderConstruction
	{
		FilterRegistrationBean<FilterSiteUsage6> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage6());
//		bean.addUrlPatterns("/~~~");
		bean.setOrder(10);
		bean.setEnabled(false);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage7> filterUsage7()  // Filter WelcomePage
	{
		FilterRegistrationBean<FilterSiteUsage7> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage7());
		bean.addUrlPatterns("/welcome");
		bean.setOrder(11);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage8> filterUsage8()  // Filter sDownloadResume
	{
		FilterRegistrationBean<FilterSiteUsage8> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage8());
		bean.addUrlPatterns("/sDownloadResume");
		bean.setOrder(12);
		
		return bean;
	}
	
	
	
	
	
}
