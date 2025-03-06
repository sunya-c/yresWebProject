package com.sunya.yresWebProject.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
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
	
	public String getWebinfo(String keyName) throws SQLException
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
			throw new SQLException("daowebdatainfo.getwebinfo-01: SQL Exception");
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
				throw new NullPointerException("daowebdatainfo.getwebinfo-02: Database connection failed.");
			}
		}
		return null;
	}

}
