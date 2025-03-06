package com.sunya.yresWebProject.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import jakarta.servlet.ServletException;

@Repository
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
	
	public boolean isBlacklited(String ip) throws SQLException
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
			throw new SQLException("daoipblacklist.isblacklisted-01: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daoipblacklist.isblacklisted-02: Database connection failed.");
			}
		}
		return false;
	}
	
	
	public boolean addToBlacklist(String ip, String countryCode) throws ServletException, SQLException
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
				throw new ServletException("daoipblacklist.addtoblacklist-01: Something went wrong.");
		}
		catch (SQLException e)
		{
			throw new SQLException("daoipblacklist.addtoblacklist-02: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daoipblacklist.addtoblacklist-03: Database connection failed.");
			}
		}
	}
	
	
	public boolean increaseCount(String ip) throws ServletException, SQLException
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
				throw new ServletException("daoipblacklist.increasecount-01: Something went wrong.");
		}
		catch (SQLException e)
		{
			throw new SQLException("daoipblacklist.increasecount-02: SQL Exception");
		}
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new ServletException("daoipblacklist.increasecount-03: Database connection failed.");
			}
		}
	}
	
	public ArrayList<String> getIp() throws SQLException
	{
		String query = "SELECT "+COLUMN_IP+" FROM "+TABLE_NAME+";";
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			
			rs = st.executeQuery();
			
			ArrayList<String> blacklistIPs = new ArrayList<>();
			while(rs.next())
			{
				blacklistIPs.add(rs.getString(COLUMN_IP));
			}
			return blacklistIPs;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new SQLException("daoipblacklist.getip-01: SQL Exception");
		}
		finally
		{
			try
			{
			st.close();
			con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				e.printStackTrace();
				throw new NullPointerException("daoipblacklist.getip-02: Database connection failed.");
			}
		}
		
	}
}
