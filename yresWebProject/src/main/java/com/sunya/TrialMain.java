package com.sunya;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sunya.config.LibraryConfig;
import com.sunya.daos.DaoAutoRemove;
import com.sunya.daos.DaoIPBlacklist;
import com.sunya.daos.DaoSiteUsage;

public class TrialMain
{
	public static void main(String[] args) throws SQLException
	{
		TrialMain main = new TrialMain();
		
		main.deleteBots();
		
//		main.autoDeleteAccount();
	}
	
	
	
	/**
	 * Run this on any machine with internet connection to start 
	 * automatic account deletion every specified period of time.
	 * 
	 * @param args
	 * @throws SQLException 
	 */
	public void autoDeleteAccount()
	{
		ApplicationContext context = new AnnotationConfigApplicationContext(LibraryConfig.class);
		DateTimeFormatter dateTimeFormat = (DateTimeFormatter)context.getBean("frontDateTime");
		
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(()->{
			System.out.println("executor run!!! -> " + LocalDateTime.now().format(dateTimeFormat));
			
			DaoAutoRemove daoRemove = new DaoAutoRemove();
			try
			{
				daoRemove.autoRemoveTempUser();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}, 0, 5, TimeUnit.MINUTES); // Run auto-delete commands, starting at 0 minute and repeating every 5 minutes.
	}
	
	/**
	 * Delete Bots from usageinfo table.
	 * 
	 * @param args
	 * @throws SQLException
	 */
	private void deleteBots() throws SQLException
	{
		PrintTime.println("Deleting bots...");
		
		DaoIPBlacklist daoBlacklist = new DaoIPBlacklist();
		DaoSiteUsage daoUsage = new DaoSiteUsage();
		
		PrintTime.println("Retrieving IPs from blacklistinfo...");
		ArrayList<String> blacklistIPs = daoBlacklist.getIp();
		
		PrintTime.println("Retrieving IPs from usageinfo...");
		ArrayList<String> usageIPs = daoUsage.getIp();
		
		PrintTime.println("Removing blacklisted IPs from usageinfo...");
		daoUsage.removeUsageByBlacklist(blacklistIPs, usageIPs);
		
		PrintTime.println("Deletion completed.");
	}
}
