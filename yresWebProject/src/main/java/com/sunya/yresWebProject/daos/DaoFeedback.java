package com.sunya.yresWebProject.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.PrintError;

import jakarta.servlet.ServletException;

@Repository
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
	
	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;
	
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
	 * @throws Exception 
	 */
	public String submitFeedback(
			String username,
			String feedbackTitle,
			String feedbackDetail,
			String feedbackErrorMessage) throws Exception
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
	 * @throws SQLException 
	 * @throws Exception 
	 */
	private String addFeedback(
			String username,
			String feedbackTitle,
			String feedbackDetail,
			String feedbackErrorMessage) throws ServletException, SQLException
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
			String reportDate = time.format(dateTimeFormat);
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
			throw new SQLException("daofeedback.addfeedback-01: SQL Exception.");
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
				throw new NullPointerException("daofeedback.addfeedback-02: Database connection failed.");
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
	 * @throws SQLException 
	 */
	private boolean isExistingRefNumber(String refNumber) throws ServletException, SQLException
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
					throw new ServletException("daofeedback.isExistingRefNumber-01: Something went wrong !!!");
			}
		}
		catch (SQLException e)
		{
			throw new SQLException("daofeedback.isexistingrefnumber-02: SQL Exception");
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
				throw new NullPointerException("daofeedback.isexistingrefnumber-03: Database connection failed.");
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
