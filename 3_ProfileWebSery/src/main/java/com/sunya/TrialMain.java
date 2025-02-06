package com.sunya;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sunya.daos.DaoAutoRemove;

public class TrialMain
{
	/**
	 * Run this on any machine with internet connection to start 
	 * automatic account deletion every specified period of time.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(()->{
			System.out.println("executor run!!! -> " + LocalDateTime.now());
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
}
