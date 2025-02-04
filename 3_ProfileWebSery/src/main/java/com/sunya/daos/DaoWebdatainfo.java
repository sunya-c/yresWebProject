package com.sunya.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;

@Component
public class DaoWebdatainfo extends Dao
{
	// Table name
	private final String TABLE_NAME = "webdatainfo";
	
	// columnName
	private final String COLUMN_KEY = "key_webinfo";
	private final String COLUMN_VALUE = "value_webinfo";
	
	{
		setupDbms("sunyadb");
	}
	
	public String getWebinfo(String keyName) throws ServletException
	{
		String query = "SELECT "+COLUMN_VALUE+", "+COLUMN_KEY+" FROM "+TABLE_NAME+" WHERE "+COLUMN_KEY+" = ?";
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			st.setString(1, keyName);
			rs = st.executeQuery();
			
			if (rs.next())
			{
				// Double check for case sensitive
				if (rs.getString(COLUMN_KEY).equals(keyName))
				{
					return rs.getString(COLUMN_VALUE);
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(">>> Exception addFeedback-01 !!! <<<");
			System.err.println(e);
			throw new ServletException("SQL Exception");
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
				System.err.println(">>> Exception addFeedback-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception addFeedback-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
			}
		}
		return null;
	}

}
