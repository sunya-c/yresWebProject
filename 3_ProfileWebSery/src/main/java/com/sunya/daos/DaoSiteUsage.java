package com.sunya.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.TimeZone;

import com.sunya.PrintError;

import jakarta.servlet.ServletException;

public class DaoSiteUsage extends Dao
{
	// tableName :
	private final String TABLE_NAME = "usageinfo";
	// end -- tableName

	// columnName :
	private final String USAGE_REF_NUMBER = "reference_number";
	private final String USAGE_DATE = "latest_interaction_date";
	private final String USAGE_PAGE_CREATEACCOUNT = "page_createaccount";
	private final String USAGE_PAGE_ERROR = "page_error";
	private final String USAGE_PAGE_FEEDBACK = "page_feedback";
	private final String USAGE_PAGE_LOGIN = "page_login";
	private final String USAGE_PAGE_PERSINFO = "page_persinfo";
	private final String USAGE_PAGE_REDIRECTING = "page_redirecting";
	private final String USAGE_PAGE_UNDERCONSTRUCTION = "page_underconstruction";
	private final String USAGE_PAGE_WELCOME = "page_welcome";
	private final String USAGE_RESUME_DOWNLOAD = "resume_download";
	// end -- columnName
	
	//columnName in Array :
	private final String[] pageNames = {
			USAGE_PAGE_CREATEACCOUNT,
			USAGE_PAGE_ERROR,
			USAGE_PAGE_FEEDBACK,
			USAGE_PAGE_LOGIN,
			USAGE_PAGE_PERSINFO,
			USAGE_PAGE_REDIRECTING,
			USAGE_PAGE_UNDERCONSTRUCTION,
			USAGE_PAGE_WELCOME,
			USAGE_RESUME_DOWNLOAD};
	// end -- columnName in Array

	{
		setupDbms("sunyadb");
	}
	
	public int getArraySize()
	{
		return pageNames.length;
	}

	/**
	 * Update usage data of the given refNumber on the database.
	 * 
	 * @param refNumber ~ enter {@code null} to create new refNumber.
	 * @param updatedUsage ~ the updated usage data for overwriting the existing one on the database.
	 * @return <strong>String of refNumber</strong> ~ if successfully updated the
	 *         database.<br>
	 *         <strong>null</strong> ~ if failed.
	 * @throws ServletException
	 */
	public String updateUsage(String refNumber, int[] updatedUsage) throws ServletException
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+USAGE_DATE+" = ?, "+USAGE_PAGE_CREATEACCOUNT+" = ?, "+USAGE_PAGE_ERROR+" = ?, "
				+USAGE_PAGE_FEEDBACK+" = ?, "+USAGE_PAGE_LOGIN+" = ?, "+USAGE_PAGE_PERSINFO+" = ?, "
				+USAGE_PAGE_REDIRECTING+" = ?, "+USAGE_PAGE_UNDERCONSTRUCTION+" = ?, "+USAGE_PAGE_WELCOME
				+" = ?, "+USAGE_RESUME_DOWNLOAD+" = ? WHERE "+USAGE_REF_NUMBER+" = ?;";

		if (refNumber == null)
			refNumber = addNewUsageRef();

		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			// values
			
			TimeZone tzone = TimeZone.getDefault();
			int timeOffset = -tzone.getRawOffset();
			int gmtPlus7 = (timeOffset/(1000*60*60))+7;  // an offset for converting local machine's time to GMT+7
			
			LocalDateTime time = LocalDateTime.now().plusHours(gmtPlus7);  // converting local machine's time to GMT+7
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String addedDate = time.format(format);
			st.setString(1, addedDate);
			
			st.setInt(2, updatedUsage[0]);
			st.setInt(3, updatedUsage[1]);
			st.setInt(4, updatedUsage[2]);
			st.setInt(5, updatedUsage[3]);
			st.setInt(6, updatedUsage[4]);
			st.setInt(7, updatedUsage[5]);
			st.setInt(8, updatedUsage[6]);
			st.setInt(9, updatedUsage[7]);
			st.setInt(10, updatedUsage[8]);
			st.setString(11, refNumber);

			row = st.executeUpdate();

			if (row == 1)
				return refNumber;
			else if (row > 1)
				throw new ServletException("More than ONE row in the database were updated!!!");
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
	
	public int[] getUsage(String refNumber) throws ServletException
	{
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+USAGE_REF_NUMBER+" = ?;";
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			
			// values
			st.setString(1, refNumber);

			rs = st.executeQuery();

			if (rs.next())
				// Double check
				if (rs.getString(USAGE_REF_NUMBER).equals(refNumber))
				{
					return getUsageArray(rs);
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
	
	private int[] getUsageArray(ResultSet rs) throws ServletException
	{
		int[] currentUsage = new int[getArraySize()];
		
		try
		{
			for (int i=0; i<getArraySize(); i++)
			{
				currentUsage[i] = rs.getInt(pageNames[i]);
			}
			return currentUsage;
		}
		catch (SQLException e)
		{
			throw new ServletException("SQL Exception");
		}
	}

	/**
	 * Add feedback to the database.
	 * 
	 * @return <strong>String refNumber</strong> ~ if successfully added to the
	 *         database.<br>
	 *         <strong>null</strong> ~ if failed.
	 * @throws ServletException
	 */
	private String addNewUsageRef() throws ServletException
	{
		String query = "INSERT INTO " + TABLE_NAME + " (" + USAGE_REF_NUMBER + ") VALUES (?);";

		String refNumber;

		Connection con = null;
		PreparedStatement st = null;
		int row = 0;
		try
		{
			con = DriverManager.getConnection(url, uname, pass);
			st = con.prepareStatement(query);
			// values
			do
				refNumber = generateRefNumber();
			while (isExistingRefNumber(refNumber));
			st.setString(1, refNumber);

			row = st.executeUpdate();

			if (row == 1)
				return refNumber;
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

	/**
	 * Check in the database if the given refNumber already exists.
	 * 
	 * @param refNumber
	 * @return <strong>true</strong> ~ if the given refNumber exists in the
	 *         database.<br>
	 *         <strong>false</strong> ~ if the given refNumber does NOT exist in the
	 *         database.
	 * @throws ServletException
	 */
	private boolean isExistingRefNumber(String refNumber) throws ServletException
	{
		String query = "SELECT " + USAGE_REF_NUMBER + " FROM " + TABLE_NAME + " WHERE " + USAGE_REF_NUMBER + " = ?";

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			// 3: Create Connection
			con = DriverManager.getConnection(url, uname, pass);

			// 4: Create Statement
			st = con.prepareStatement(query);
			st.setString(1, refNumber);

			// 5: Execute the query
			rs = st.executeQuery();

			// 6: Get the results
			if (rs.next())
			{
				// Double check
				if (rs.getString(USAGE_REF_NUMBER).equals(refNumber))
					return true;
				else
					throw new ServletException("Something went wrong. isExistingRefNumber-01 !!!");
			}
		}
		catch (SQLException e)
		{
			System.err.println(">>> Exception isExistingRefNumber-02 !!! <<<");
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
				System.err.println(">>> Exception isExistingRefNumber-03 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(e);
				throw new ServletException("Database connection failed.");
			}
			catch (NullPointerException ne)
			{
				System.err.println(">>> Exception isExistingRefNumber-04 !!! <<<");
				System.err.println("Either 'Statement' or 'Connection' cannot be closed.");
				System.err.println(ne);
				throw new ServletException("Database connection failed.");
			}
		}
		return false;
	}

	/**
	 * Generate a 10-digit hash, for reference in <i>usageinfo</i> table.
	 * 
	 * @return <strong>a string of 10-digit reference number</strong>
	 */
	private String generateRefNumber()
	{
		Random random = new Random();
		String refNumber = String.valueOf(random.nextInt(1, 10));

		for (int i = 0; i < 9; i++)
			refNumber += String.valueOf(random.nextInt(0, 10));

		return refNumber;
	}

}
