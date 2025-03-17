package com.sunya.yresWebProject;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sunya.yresWebProject.daos.DaoAutoRemove;
import com.sunya.yresWebProject.daos.DaoIPBlacklist;
import com.sunya.yresWebProject.daos.DaoSiteUsage;


////@SpringBootApplication
//public class DataRemoveMain
//{
//	private static ConfigurableApplicationContext context;
//	
//	public static void main(String[] args) throws SQLException
//	{
//		DataRemoveMain main = new DataRemoveMain();
//		
//		context = SpringApplication.run(DataRemoveMain.class, args);
//		
//		System.out.println("in main !!!");
//		main.deleteBots();
//		
////		main.autoDeleteAccount();
//	}
//	
//	
//	
//	/**
//	 * Run this on any machine with internet connection to start 
//	 * automatic account deletion every specified period of time.
//	 * 
//	 * @param args
//	 * @throws SQLException 
//	 */
//	public void autoDeleteAccount()
//	{
//		DateTimeFormatter dateTimeFormat = (DateTimeFormatter)context.getBean("frontDateTime");
//		
//		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//		executor.scheduleAtFixedRate(()->{
//			System.out.println("executor run!!! -> " + LocalDateTime.now().format(dateTimeFormat));
//			
//			DaoAutoRemove daoRemove = context.getBean(DaoAutoRemove.class);
//			try
//			{
//				daoRemove.autoRemoveTempUser();
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}, 0, 5, TimeUnit.MINUTES); // Run auto-delete commands, starting at 0 minute and repeating every 5 minutes.
//	}
//	
//	/**
//	 * Delete Bots from usageinfo table.
//	 * 
//	 * @param args
//	 * @throws SQLException
//	 */
//	private void deleteBots()
//	{
//		PrintTime.println("Deleting bots...");
//		
//		DaoIPBlacklist daoBlacklist = context.getBean(DaoIPBlacklist.class);
//		DaoSiteUsage daoUsage = context.getBean(DaoSiteUsage.class);
//		
//		PrintTime.println("Retrieving IPs from blacklistinfo...");
//		ArrayList<String> blacklistIPs = daoBlacklist.getIp();
//		
//		PrintTime.println("Retrieving IPs from usageinfo...");
//		ArrayList<String> usageIPs = daoUsage.getIPs();
//		
//		PrintTime.println("Removing blacklisted IPs from usageinfo...");
//		daoUsage.removeUsageByBlacklist(blacklistIPs, usageIPs);
//		
//		PrintTime.println("Deletion completed.");
//	}
//}
