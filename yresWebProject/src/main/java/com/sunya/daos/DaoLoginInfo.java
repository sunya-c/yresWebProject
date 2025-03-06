package com.sunya.daos;

// 1: Import
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.sunya.PrintError;

import jakarta.servlet.ServletException;

@Repository
public class DaoLoginInfo extends Dao
{
	// Table name :
	protected final String TABLE_NAME = "logininfo";
	
	
	// columnName :
	protected final String COLUMN_USERNAME = "webuname";
	protected final String COLUMN_PASSWORD = "webpass";
	protected final String COLUMN_TEMPACCOUNT = "tempaccount";
	protected final String COLUMN_TIMECREATED = "timecreated";
	// end -- columnName
	
	// Error report :
	final String ERR1 = "Username already exists";
	final String ERR2 = "Invalid username";
	final String ERR3 = "Incorrect password";
	String errText = null;
	// end -- Error report
	
	@Autowired
	@Qualifier("backDateTime")
	protected DateTimeFormatter dateTimeFormat;


	{
		setupDbms("sunyadb");
	}
	

	public void getData() throws SQLException
	{
		String query1 = "SELECT * FROM "+TABLE_NAME+";";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.createStatement();

			// 5: Execute the query
			rs = st.executeQuery(query1);

			// 6: Get the results
			while (rs.next())
			{
				System.out.print("Username: " + rs.getString(COLUMN_USERNAME) + ", ");
				System.out.println("Password: " + rs.getString(COLUMN_PASSWORD));
			}
		}
		catch (SQLException e)
		{
			throw new SQLException("daologininfo.getdata-01: SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daologininfo.getdata-02: Database connection failed.");
			}
		}
	}

	/**
	 * Remove all data of a particular username from the database (password
	 * required). The removed username MUST NOT be admin privileged accounts
	 * <p>
	 * 
	 * @param username ~ username to be removed.
	 * @param password ~ password of the username that is going to be removed.
	 * @return true ~ removal succeed.
	 *         <p>
	 *         false ~ removal fail.
	 * @throws SQLException 
	 * @throws ServletException 
	 */
	public boolean removeUser(String username, String password) throws SQLException, ServletException
	{
		if (isExistingPasswordCaseSen(username, password))
		{
			String query1 = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ? AND "+COLUMN_TEMPACCOUNT+" = ?";

			Connection con = null;
			PreparedStatement st = null;
			int row = 0;
			try
			{
				// 3: Create Connection
				con = DriverManager.getConnection(url, uname, pass);

				// 4: Create Statement
				st = con.prepareStatement(query1);
				st.setString(1, username);
				st.setString(2, password);
				st.setString(3, "1");

				// 5: Execute the query
				row = st.executeUpdate();

				// 6: Get the results
				System.out.println("Data removed!!! Rows affected: " + row);
				if (row == 1)
					return true;
				else if (row >= 1)
					throw new ServletException("daologininfo.removeuser-01: Something went wrong. More than one account removed.");
			}
			catch (SQLException e)
			{
				throw new SQLException("daologininfo.removeuser-02: SQL Exception");
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
					throw new NullPointerException("daologininfo.removeduser-03: Database connection failed.");
				}
			}
		}
		return false;
	}

	/**
	 * Add the username and password to the database.
	 * <p>
	 * 
	 * @param username ~ the username to be added.
	 *                 <p>
	 * @param password ~ the password to be added.
	 *                 <p>
	 * @return true ~ if succeed.
	 *         <p>
	 *         false ~ if the username is duplicated.
	 * @throws SQLException 
	 */
	public boolean addUser(String username, String password) throws SQLException
	{
		if (!isExistingUsername(username))
		{
			String query1 = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_USERNAME+", "+COLUMN_PASSWORD+", "+COLUMN_TIMECREATED+") VALUES (?, ?, ?);";

			Connection con = null;
			PreparedStatement st = null;
			int row = 0;
			try
			{
				// 3: Create Connection
				con = DriverManager.getConnection(url, uname, pass);

				// 4: Create Statement
				st = con.prepareStatement(query1);
				st.setString(1, username);
				st.setString(2, password);

				TimeZone tzone = TimeZone.getDefault();
				int timeOffset = -tzone.getRawOffset();
				int gmtPlus7 = (timeOffset/(1000*60*60))+7;  // an offset for converting local machine's time to GMT+7
				
				LocalDateTime time = LocalDateTime.now().plusHours(gmtPlus7);  // converting local machine's time to GMT+7
				String formattedTime = time.format(dateTimeFormat);
				st.setString(3, formattedTime);

				// 5: Execute the query
				row = st.executeUpdate();

				// 6: Get the results
				System.out.println("Data added!!! Rows affected: " + row);
				if (row == 1)
					return true;
			}
			catch (SQLException e)
			{
				throw new SQLException("daologininfo.adduser-01: SQL Exception");
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
					throw new NullPointerException("daologininfo.adduser-02: Database connection failed.");
				}
			}
		}
		else
		{
			errText = ERR1; // Username already exists
			PrintError.println(errText);
		}

		return false;
	}

	/**
	 * Check if the input username exists (NOT case-sensitive).
	 * <p>
	 * 
	 * @param username ~ input username to be check.
	 *                 <p>
	 * @return true ~ if the username exists regardLESS of upper and lower case.
	 *         <p>
	 *         false ~ otherwise.
	 * @throws SQLException 
	 */
	private boolean isExistingUsername(String username) throws SQLException
	{
		// For username checking
		String query1 = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query1);
			st.setString(1, username);

			// 5: Execute the query
			rs = st.executeQuery();

			// 6: Get the results
			// NOT case-sensitive
			if (rs.next())
				return true;
		}
		catch (SQLException e)
		{
			throw new SQLException("daologininfo.isexistingusername-01: SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daologininfo.isexistingusername-02: Database connection failed.");
			}
		}
		return false;
	}

	/**
	 * Check if the input username exists (case-sensitive)
	 * <p>
	 * 
	 * @param username ~ input username to be check.
	 *                 <p>
	 * @return true ~ if the username case-sensitively exists.
	 *         <p>
	 *         false ~ otherwise.
	 * @throws SQLException 
	 */
	private boolean isExistingUsernameCaseSen(String username) throws SQLException
	{
		// For username checking
		String query1 = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query1);
			st.setString(1, username);

			// 5: Execute the query
			rs = st.executeQuery();

			// 6: Get the results
			// NOT case-sensitive
			if (rs.next())
			{
				// Double check for case-sensitive
				if (rs.getString("webuname").equals(username))
					return true;
			}
		}
		catch (SQLException e)
		{

			throw new SQLException("daologininfo.isexistingusernamecasesen-01: SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daologininfo.isexistingusernamecasesen-02: Database connection failed.");
			}
		}
		return false;
	}

	/**
	 * Check if the password for the particular username exists (case-sensitive for
	 * both username and password). Recommend calling isExistingUsernameCaseSen()
	 * first to check the existence of the username before calling this method, as
	 * this method will return false right away if the username doesn't exist.
	 * <p>
	 * 
	 * @param username ~ input username to be check.
	 * @param password ~ input password for the particular username.
	 *                 <p>
	 * @return true ~ if BOTH username AND password case-sensitively exist.
	 *         <p>
	 *         false ~ otherwise.
	 * @throws SQLException 
	 */
	private boolean isExistingPasswordCaseSen(String username, String password) throws SQLException
	{
		// For password checking
		String query1 = "SELECT "+COLUMN_USERNAME+", "+COLUMN_PASSWORD+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ?;";

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query1);
			st.setString(1, username);
			st.setString(2, password);

			// 5: Execute the query
			rs = st.executeQuery();

			// 6: Get the results
			// NOT case-sensitive
			if (rs.next())
			{
				// Double check for case-sensitive
				if (rs.getString(COLUMN_USERNAME).equals(username) && rs.getString(COLUMN_PASSWORD).equals(password))
					return true;
			}
		}
		catch (SQLException e)
		{
			throw new SQLException("daologininfo.isexistingpasswordcasesen-01: SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException | NullPointerException e)
			{
				throw new NullPointerException("daologininfo.isexistingpasswordcasesen-02: Database connection failed.");
			}
		}
		return false;
	}

	/**
	 * 
	 * @param username
	 * <p>
	 * @return true ~ if the username exists regardLESS of upper or lower case.
	 * <p>     false ~ otherwise.
	 * @throws SQLException 
	 */
	public boolean checkUsername(String username) throws SQLException
	{
		if (isExistingUsername(username))
			return true;
		else
		{
			errText = ERR2; // Invalid username
			PrintError.println(errText);
			return false;
		}
	}
	
	/**
	 * 
	 * @param username
	 * <p>
	 * @return true ~ if the username case-sensitively exists.
	 * <p>     false ~ otherwise.
	 * @throws SQLException 
	 */
	public boolean checkUsernameCaseSen(String username) throws SQLException
	{
		if (isExistingUsernameCaseSen(username))
			return true;
		else
		{
			errText = ERR2; // Invalid username
			PrintError.println(errText);
			return false;
		}
	}

	public boolean checkPasswordCaseSen(String username, String password) throws SQLException
	{
		if (isExistingPasswordCaseSen(username, password))
			return true;
		else
		{
			errText = ERR3; // Incorrect password
			PrintError.println(errText);
			return false;
		}
	}

}
