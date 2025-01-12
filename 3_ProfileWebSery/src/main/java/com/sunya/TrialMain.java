package com.sunya;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sunya.daos.DaoAutoRemove;

public class TrialMain
{

	public static void main(String[] args) throws SQLException
	{
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(()->{
			System.out.println("executor run!!! -> " + LocalDateTime.now());
			DaoAutoRemove daoRemove = new DaoAutoRemove();
			try
			{
				daoRemove.autoRemoveTempUser();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}, 0, 5, TimeUnit.MINUTES);
	}

}
