package com.sunya.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.sunya.PrintError;

public class DaoAutoRemove extends DaoLoginInfo
{

	/**
	 * Delete the user which <strong>username</strong> AND <strong>password</strong> AND <strong>time created</strong> match the argument passed into this method. 
	 * 
	 * @param username
	 * @param password
	 * @param sqlDateTime
	 * @return
	 * @throws Exception 
	 */
	private boolean removeTempUser(String username, String password, String sqlDateTime) throws Exception
	{
		String query = "DELETE FROM logininfo WHERE "+COLUMN_TEMPACCOUNT+" = ? AND "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ? AND "+COLUMN_TIMECREATED+" = ?";

		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query);
			st.setString(1, "1");
			st.setString(2, username);
			st.setString(3, password);
			st.setString(4, sqlDateTime);

			// 5: Execute the query
			row = st.executeUpdate();

			// 6: Get the results
			System.out.println("Data removed!!! Rows affected: " + row);
			return true;
		}
		catch (SQLException e)
		{
			System.err.println(">>> Exception removedData-01 !!! <<<");
			System.err.println(e);
		}
		// 7: Close the Statement and Connection
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				PrintError.println(">>> Exception removedData-02 !!! <<<\n"
						+ "Either 'Statement' or 'Connection' cannot be closed.\n"
						+ e);
				throw e;
			}
		}

		return false;
	}
	

	/**
	 * Automatically delete the user <i>60</i> minutes after registration
	 * @throws Exception 
	 */
	public void autoRemoveTempUser() throws Exception
	{
		String query1 = "SELECT "+COLUMN_USERNAME+", "+COLUMN_PASSWORD+", "+COLUMN_TIMECREATED+" FROM "+TABLE_NAME+" WHERE "+COLUMN_TEMPACCOUNT+" = ?";

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query1);
			st.setString(1, "1");

			// 5: Execute the query
			rs = st.executeQuery();

			TimeZone tzone = TimeZone.getDefault();
			int timeOffset = -tzone.getRawOffset();
			int gmtPlus7 = (timeOffset/(1000*60*60))+7;  // an offset for converting local machine's time to GMT+7
			LocalDateTime timeNow = LocalDateTime.now().plusHours(gmtPlus7);  // converting local machine's time to GMT+7
			
			// 6: Get the results
			while (rs.next())
			{
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime timeCreated = LocalDateTime.parse(rs.getString(COLUMN_TIMECREATED), timeFormat);
				Duration duration = Duration.between(timeCreated, timeNow);
				if (duration.toMinutes() >= 60)
				{
					removeTempUser(rs.getString(COLUMN_USERNAME), rs.getString(COLUMN_PASSWORD), rs.getString(COLUMN_TIMECREATED));
					System.out.println(timeCreated);
					System.out.println(timeNow);
					System.out.println(duration.toDays());
				}
			}
		}
		catch (SQLException e)
		{
			PrintError.println(">>> Exception autoremovetempuser-01 !!! <<<\n"
					+ e);
			throw new SQLException("SQL Exception");
		}
		// 7: Close the Statement and Connection
		finally
		{
			try
			{
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception getTimeCreated-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new NullPointerException("Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception removeUser-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new NullPointerException("Database connection failed.");
			}
		}
	}

}
