package com.sunya.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;

import jakarta.servlet.ServletException;

public class DaoAutoRemove extends DaoLoginInfo
{

	// Automatically delete the user 60 minutes after registration
	private boolean removeTempUser(String username, String password, String sqlDateTime)
	{
//		String query1 = "DELETE FROM logininfo WHERE " + USERNAME + " = ? AND " + PASSWORD + " = ? AND " + " tempaccount = ?";
		String query2 = "DELETE FROM logininfo WHERE tempaccount = '1' AND timecreated = ?";

		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query2);
//			st.setString(1, username);
//			st.setString(2, password);
			st.setString(1, sqlDateTime);

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
			catch (SQLException e)
			{
				System.err.println(">>> Exception removedData-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
			}
		}

		return false;
	}
	
	// Get the time
	public void autoRemoveTempUser() throws SQLException
	{
		String query1 = "SELECT timecreated FROM logininfo WHERE tempaccount = ?";

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

			LocalDateTime timeNow = LocalDateTime.now();
			// 6: Get the results
			while (rs.next())
			{
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime timeCreated = LocalDateTime.parse(rs.getString("timecreated"), timeFormat);
				Duration duration = Duration.between(timeCreated, timeNow);
				if (duration.toMinutes() >= 60)
				{
					removeTempUser("", "", rs.getString("timecreated"));
					System.out.println(timeCreated);
					System.out.println(timeNow);
					System.out.println(duration.toDays());
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(">>> Exception getTimeCreated-01 !!! <<<");
			System.err.println(e);
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
