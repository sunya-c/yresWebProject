package com.sunya.daos;

import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;

public class DaoIPBlacklist extends Dao
{
	// tableName :
	private final String TABLE_NAME = "ipblacklistinfo";
	
	// columnName :
	private final String COLUMN_IP = "ip_address";
	private final String COLUMN_COUNTRY = "country_code";
	private final String COLUMN_COUNT = "block_count";
	
	
	{
		setupDbms("sunyadb");
	}
	
	public boolean isBlacklited(String ip) throws ServletException
	{
		String query = "SELECT "+COLUMN_IP+" FROM "+TABLE_NAME+" WHERE "+COLUMN_IP+" = ?;";
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			st.setString(1, ip);
			rs = st.executeQuery();
			
			if (rs.next())
			{
				// Double check for case sensitive
				if (rs.getString(COLUMN_IP).equals(ip))
				{
					return true;
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(">>> daoipblacklist.isBlacklisted-01: SQL Exception!!! <<<");
			System.err.println(e);
			throw new ServletException("<br>daoipblacklist.isBlacklisted-01: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> daoipblacklist.isBlacklisted-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("<br>daoipblacklist.isBlacklisted-01: Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> daoipblacklist.isBlacklisted-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("<br>daoipblacklist.isBlacklisted-03: Database connection failed.");
			}
		}
		return false;
	}
	
	
	public boolean addToBlacklist(String ip, String countryCode) throws ServletException
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_IP+", "+COLUMN_COUNTRY+") VALUES (?, ?);";
		
		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			st.setString(1, ip);
			st.setString(2, countryCode);
			row = st.executeUpdate();
			
			if (row == 1)
				return true;
			else
				throw new ServletException("<br>daoipblacklist.addToBlacklist-01: Something went wrong.");
		}
		catch (SQLException e)
		{
			System.err.println(">>> daoipblacklist.addToBlacklist-02 !!! <<<");
			System.err.println(e);
			throw new ServletException("<br>daoipblacklist.addToBlacklist-02: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> daoipblacklist.addToBlacklist-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("<br>daoipblacklist.addToBlacklist-03: Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> daoipblacklist.addToBlacklist-04 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("<br>daoipblacklist.addToBlacklist-04: Database connection failed.");
			}
		}
	}
	
	
	public boolean increaseCount(String ip) throws ServletException
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_COUNT+" = "+COLUMN_COUNT+" + ? WHERE "+COLUMN_IP+" = ?";
		
		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			st.setInt(1, 1);
			st.setString(2, ip);
			
			row = st.executeUpdate();
			
			if (row == 1)
				return true;
			else
				throw new ServletException("<br>daoipblacklist.increasecount-01: Something went wrong.");
		}
		catch (SQLException e)
		{
			System.err.println(">>> daoipblacklist.increasecount-02 !!! <<<");
			System.err.println(e);
			throw new ServletException("<br>daoipblacklist.increasecount-02: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> daoipblacklist.increasecount-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("<br>daoipblacklist.increasecount-02: Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> daoipblacklist.increasecount-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("<br>daoipblacklist.increasecount-03: Database connection failed.");
			}
		}
	}
}
