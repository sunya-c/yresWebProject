package com.sunya.yresWebProject.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.filters.FilterAccountExistence;
import com.sunya.yresWebProject.filters.FilterSetCookie;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage0;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage1;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage10;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage11;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage12;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage2;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage3;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage4;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage5;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage6;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage7;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage8;
import com.sunya.yresWebProject.filters.siteUsage.FilterSiteUsage9;
import com.sunya.yresWebProject.managers.CookieManager;
import com.sunya.yresWebProject.managers.SessionManager;

@Configuration
public class FilterConfig
{
	@Autowired
	private SessionManager sm;
	private Set<String> allPaths = Set.of("/accountInfo",
											"/accountInfo/*",
											"/adminPanel",
											"/adminPanel/*",
											"/createAccount",
											"/yresError",
											"/feedback",
											"/feedback/summary",
											"/Home",
											"/personalInformation",
											"/redirecting",
											"/restApi",
											"/webHistory",
											"/welcome",
											"/sCreateAccount",
											"/sDownloadResume",
											"/sFeedback",
											"/sLogin",
											"/sLogout");
	
	
	@Bean
	public FilterRegistrationBean<FilterSetCookie> filterSetCookie(DaoLoginInfo dao, CookieManager cm)
	{
		FilterRegistrationBean<FilterSetCookie> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSetCookie(cm, sm));
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterAccountExistence> filterAccExistence(DaoLoginInfo dao, CookieManager cm)
	{
		FilterRegistrationBean<FilterAccountExistence> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterAccountExistence(sm, dao, cm));
		bean.setUrlPatterns(allPaths);
		bean.setOrder(2);
		
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
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage1> filterUsage1()  // Filter ErrorPage
	{
		FilterRegistrationBean<FilterSiteUsage1> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage1(siteUsage));
		bean.addUrlPatterns("/yresError");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage2> filterUsage2()  // Filter FeedbackPage
	{
		FilterRegistrationBean<FilterSiteUsage2> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage2(siteUsage));
		bean.addUrlPatterns("/feedback", "/feedback/summary");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage3> filterUsage3()  // Filter Home/Login
	{
		FilterRegistrationBean<FilterSiteUsage3> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage3(siteUsage));
		bean.addUrlPatterns("/Home");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage4> filterUsage4()  // Filter PersInfoPage
	{
		FilterRegistrationBean<FilterSiteUsage4> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage4(siteUsage));
		bean.addUrlPatterns("/personalInformation");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage5> filterUsage5()  // Filter RedirectingPage
	{
		FilterRegistrationBean<FilterSiteUsage5> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage5(siteUsage));
		bean.addUrlPatterns("/redirecting");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage6> filterUsage6()  // Filter UnderConstruction
	{
		FilterRegistrationBean<FilterSiteUsage6> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage6(siteUsage));
		bean.addUrlPatterns("");
		bean.setOrder(5);
		bean.setEnabled(false);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage7> filterUsage7()  // Filter WelcomePage
	{
		FilterRegistrationBean<FilterSiteUsage7> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage7(siteUsage));
		bean.addUrlPatterns("/welcome");
		bean.setOrder(5);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage8> filterUsage8()  // Filter sDownloadResume
	{
		FilterRegistrationBean<FilterSiteUsage8> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage8(siteUsage));
		bean.addUrlPatterns("/sDownloadResume");
		bean.setOrder(5);
		
		return bean;
	}
	
	
	
	@Bean
	public FilterRegistrationBean<FilterSiteUsage9> filterUsage9()  // Filter RestApiPage
	{
		FilterRegistrationBean<FilterSiteUsage9> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage9(siteUsage));
		bean.addUrlPatterns("/restApi");
		bean.setOrder(5);
		
		return bean;
	}
	@Bean
	public FilterRegistrationBean<FilterSiteUsage10> filterUsage10()  // Filter WebHistoryPage
	{
		FilterRegistrationBean<FilterSiteUsage10> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage10(siteUsage));
		bean.addUrlPatterns("/webHistory");
		bean.setOrder(5);
		
		return bean;
	}
	@Bean
	public FilterRegistrationBean<FilterSiteUsage11> filterUsage11()  // Filter AdminPanelPage
	{
		FilterRegistrationBean<FilterSiteUsage11> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage11(siteUsage));
		bean.addUrlPatterns("/adminPanel");
		bean.setOrder(5);
		
		return bean;
	}
	@Bean
	public FilterRegistrationBean<FilterSiteUsage12> filterUsage12()  // Filter AccountInfoPage
	{
		FilterRegistrationBean<FilterSiteUsage12> bean = new FilterRegistrationBean<>();
		bean.setFilter(new FilterSiteUsage12(siteUsage));
		bean.addUrlPatterns("/accountInfo");
		bean.setOrder(5);
		
		return bean;
	}
}
