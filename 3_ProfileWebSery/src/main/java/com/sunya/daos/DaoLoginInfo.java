package com.sunya.daos;

// 1: Import
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.sunya.PrintError;
import com.sunya.exceptions.WebUnameException;

import jakarta.servlet.ServletException;

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


	{
		setupDbms("sunyadb");
	}
	

	public void getData() throws ServletException
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
			System.err.println(">>> Exception getData-01 !!! <<<");
			System.err.println(e);
			throw new ServletException("SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception getData-02 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception getData-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
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
	 * @throws ServletException
	 */
	public boolean removeUser(String username, String password) throws ServletException
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
					throw new ServletException("Something went wrong. More than one account removed.");
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception removedUser-01 !!! <<<");
				System.err.println(e);
				throw new ServletException("SQL Exception");
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
					System.err.println(">>> Exception removedUser-02 !!! <<<");
					System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
					System.err.println(e);
					throw new ServletException("Database connection failed.");
				}
				catch (NullPointerException ne)
				{
					System.err.println(">>> Exception removeUser-03 !!! <<<");
					System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
					System.err.println(ne);
					throw new ServletException("Database connection failed.");
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
	 * @throws ServletException
	 */
	public boolean addUser(String username, String password) throws ServletException
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
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedTime = time.format(timeFormat);
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
				System.err.println(">>> Exception addUser-01 !!! <<<");
				System.err.println(e);
				throw new ServletException("SQL Exception");
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
					System.err.println(">>> Exception addUser-02 !!! <<<");
					System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
					System.err.println(e);
					throw new ServletException("Database connection failed.");
				}
				catch (NullPointerException ne)
				{
					System.err.println(">>> Exception addUser-03 !!! <<<");
					System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
					System.err.println(ne);
					throw new ServletException("Database connection failed.");
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
	 *         false ~ otherwise
	 * @throws ServletException
	 */
	private boolean isExistingUsername(String username) throws ServletException
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
			System.err.println(">>> Exception existsUsername-01 !!! <<<");
			System.err.println(e);
			throw new ServletException("SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception existsUsername-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Exception existsUsername-03");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception existsUsername-04 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
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
	 * @throws ServletException
	 */
	private boolean isExistingUsernameCaseSen(String username) throws ServletException
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
			System.err.println(">>> Exception existsUsername-01 !!! <<<");
			System.err.println(e);
			throw new ServletException("SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception existsUsername-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Exception existsUsername-03");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception existsUsername-04 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
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
	 * @throws ServletException
	 */
	private boolean isExistingPasswordCaseSen(String username, String password) throws ServletException
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
			System.err.println(">>> Exception existsPassword-01 !!! <<<");
			System.err.println(e);
			throw new ServletException("SQL Exception");
		}
		finally
		{
			try
			{
				// 7: Close the Statement and Connection
				st.close();
				con.close();
			}
			catch (SQLException e)
			{
				System.err.println(">>> Exception existsPassword-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception existsPassword-04 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
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
	 * @throws ServletException
	 */
	public boolean checkUsername(String username) throws ServletException
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
	 * @throws ServletException
	 */
	public boolean checkUsernameCaseSen(String username) throws ServletException
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

	public boolean checkPasswordCaseSen(String username, String password) throws ServletException
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
