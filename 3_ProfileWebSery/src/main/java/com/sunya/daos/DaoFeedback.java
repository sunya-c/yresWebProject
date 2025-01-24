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

public class DaoFeedback extends Dao
{
	// tableName :
	private final String TABLE_NAME = "feedbackinfo";
	// end -- tableName
	
	// columnName :
	private final String COLUMN_REF_NUMBER = "reference_number";
	private final String COLUMN_DATE = "report_date";
	private final String USERNAME = "webuname";
	private final String COLUMN_TITLE = "title";
	private final String COLUMN_DETAIL = "detail";
	private final String COLUMN_ERRORMESSAGE = "error_message";
	// end -- columnName
	
	private final String ERR1 = "Failed to submit feedback/bug report.";
	private String errText = "";
	
	{
		setupDbms("sunyadb");
	}

	/**
	 * Submit feedback/bug report.
	 * 
	 * @param username ~ username of the reporter if available.
	 * @param feedbackTitle ~ Title of the report.
	 * @param feedbackDetail ~ Detail of the report.
	 * @param feedbackErrorMessage ~ Error message for this issue (optional).
	 * @return <strong>String refNumber</strong> ~ if successfully added to the database.<br>
	 *         <strong>null</strong> ~ if failed.
	 * @throws ServletException
	 */
	public String submitFeedback(
			String username,
			String feedbackTitle,
			String feedbackDetail,
			String feedbackErrorMessage) throws ServletException
	{
		String refNumber = addFeedback(
				username,
				feedbackTitle,
				feedbackDetail,
				feedbackErrorMessage);
		if (refNumber == null)
		{
			errText = ERR1;
			PrintError.println(errText);
			return null;
		}
		else
			return refNumber;
	}

	
	/**
	 * Add feedback to the database.
	 * 
	 * @param username
	 * @param feedbackTitle
	 * @param feedbackDetail
	 * @param feedbackErrorMessage
	 * @return <strong>String refNumber</strong> ~ if successfully added to the database.<br>
	 *         <strong>null</strong> ~ if failed.
	 * @throws ServletException
	 */
	private String addFeedback(
			String username,
			String feedbackTitle,
			String feedbackDetail,
			String feedbackErrorMessage) throws ServletException
	{
		String query = "INSERT INTO "+TABLE_NAME
				+ " ("+COLUMN_REF_NUMBER+", "+COLUMN_DATE+", "+USERNAME+", "+COLUMN_TITLE+", "+COLUMN_DETAIL+", "+COLUMN_ERRORMESSAGE+") "
						+ "VALUES (?, ?, ?, ?, ?, ?);";
		
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
			
			TimeZone tzone = TimeZone.getDefault();
			int timeOffset = -tzone.getRawOffset();
			int gmtPlus7 = (timeOffset/(1000*60*60))+7;  // an offset for converting local machine's time to GMT+7
			
			LocalDateTime time = LocalDateTime.now().plusHours(gmtPlus7);  // converting local machine's time to GMT+7
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String reportDate = time.format(format);
			st.setString(2, reportDate);
			
			st.setString(3, username);
			st.setString(4, feedbackTitle);
			st.setString(5, feedbackDetail);
			st.setString(6, feedbackErrorMessage);
			
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
	 * @return <strong>true</strong> ~ if the given refNumber exists in the database.<br>
	 *         <strong>false</strong> ~ if the given refNumber does NOT exist in the database.
	 * @throws ServletException
	 */
	private boolean isExistingRefNumber(String refNumber) throws ServletException
	{
		String query = "SELECT "+COLUMN_REF_NUMBER+" FROM "+TABLE_NAME+" WHERE "+COLUMN_REF_NUMBER+" = ?";
		

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
				if (rs.getString(COLUMN_REF_NUMBER).equals(refNumber))
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
	 * Generate a 7-digit hash, for reference in <i>feedbackinfo</i> table.
	 * 
	 * @return <strong>a string of 7-digit reference number</strong>
	 */
	private String generateRefNumber()
	{
		Random random = new Random();
		String refNumber = String.valueOf(random.nextInt(1, 10));
		
		for (int i=0; i<6; i++)
			refNumber += String.valueOf(random.nextInt(0, 10));
		
		return refNumber;
	}

}
