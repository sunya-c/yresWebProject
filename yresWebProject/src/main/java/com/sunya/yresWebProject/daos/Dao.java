package com.sunya.yresWebProject.daos;

@Deprecated
public class Dao
{
	protected String url;
	protected String uname;
	protected String pass;
	
	// url format for aws ~ jdbc:mysql://endpoint:port/database_name
	/**
	 * <i>1. Specify database url, uname, and pass.</i><br>
	 * url = "...";<br>
	 * uname = "...";<br>
	 * pass = "...";
	 * <p>
	 * <p>
	 * <i>2. initiate the mysql driver.</i><br>
	 * Class.forName("com.mysql.cj.jdbc.Driver");
	 */
	protected void setupDbms(String databaseName)
	{
		/*
		url = "jdbc:mysql://localhost:3306/sunyadb";
		uname = "root";
		pass = "0909";
		*/

		// environment variable format for url: jdbc:mysql://url:3306/
		
		if (System.getenv("SERY_DB_URL") == null)
		{
			url = System.getProperty("SERY_DB_URL") + databaseName;
			uname = System.getProperty("SERY_DB_UNAME");
			pass = System.getProperty("SERY_DB_PASS");
		}
		else
		{
			url = System.getenv("SERY_DB_URL") + databaseName;
			uname = System.getenv("SERY_DB_UNAME");
			pass = System.getenv("SERY_DB_PASS");
		}
		
		// 2: Initiate the driver
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			System.err.println(">>> Exception setupDbms-01 !!! <<<");
			System.err.println(e);
		}
	}
}
