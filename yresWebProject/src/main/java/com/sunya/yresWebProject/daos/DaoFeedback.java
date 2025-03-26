package com.sunya.yresWebProject.daos;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelFeedback;

@Repository
public class DaoFeedback
{
	// tableName :
	private final String TABLE_NAME = "feedbackinfo";
	// end -- tableName
	
	// columnName :
	private final String COLUMN_REF_NUMBER = "reference_number";
	private final String COLUMN_DATE = "report_date";
	private final String COLUMN_USERNAME = "webuname";
	private final String COLUMN_TITLE = "title";
	private final String COLUMN_DETAIL = "detail";
	private final String COLUMN_ERRORMESSAGE = "error_message";
	// end -- columnName
	
//	private final String ERR1 = "Failed to submit feedback/bug report.";
	
	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;
	
	@Autowired
	private JdbcTemplate template;
	
	
	/**
	 * Submit feedback/bug report.
	 * 
	 * @param model ~ A model that contains feedback detail to be added to the database.
	 * @return <strong>String refNumber</strong> ~ if successfully added to the database.
	 * @throws SomethingWentWrongException ~ if otherwise.
	 */
	public String addFeedback(ModelFeedback model) throws SomethingWentWrongException
	{
		String query = "INSERT INTO "+TABLE_NAME
				+ " ("+COLUMN_REF_NUMBER+", "+COLUMN_DATE+", "+COLUMN_USERNAME+", "+COLUMN_TITLE+", "+COLUMN_DETAIL+", "+COLUMN_ERRORMESSAGE+") "
						+ "VALUES (?, ?, ?, ?, ?, ?);";
		
		String refNumber;
		do
		{
			refNumber = generateRefNumber();
		}
		while (doesExistRefNumber(refNumber));
		
		//Prepare the model
		model.setRefNumber(refNumber);
		TimeZone timeZone = TimeZone.getDefault();
		LocalDateTime dateTime = LocalDateTime.now()
				.minus(timeZone.getRawOffset(), ChronoUnit.MILLIS) // minus an offset (time zone of this local machine) to make it GMT+0.
				.plusHours(7);                                     // converting local machine's time to GMT+7
		String reportDate = dateTime.format(dateTimeFormat);
		model.setReportDate(reportDate);
		
		//Set model to prepared statement
		PreparedStatementCreator psc = con -> {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, model.getRefNumber());
			st.setString(2, model.getReportDate());
			st.setString(3, model.getUsername());
			st.setString(4, model.getFeedbackTitile());
			st.setString(5, model.getFeedbackDetail());
			st.setString(6, model.getFeedbackErrorMessage());
			return st;
		};
		
		int row;
		try
		{
			row = template.update(psc);
		}
		catch (DataAccessException e)
		{
			if (doesExistRefNumber(refNumber))
				throw new SomethingWentWrongException("daofeedback.addfeedback-01: Something went wrong.");
			else
				throw new YresDataAccessException("daofeedback.addfeedback-01");
		}
		
		if (row != 1)
			throw new YresDataAccessException("daofeedback.addfeedback-02");
		
		return refNumber;
	}
	
	/**
	 * Check in the database if the given <strong>refNumber</strong> exists.
	 * 
	 * @param refNumber ~ The refNumber to be checked.
	 * @return <strong>true</strong> ~ if the given refNumber exists in the database.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	private boolean doesExistRefNumber(String refNumber)
	{
		String query = "SELECT "+COLUMN_REF_NUMBER+" FROM "+TABLE_NAME+" WHERE "+COLUMN_REF_NUMBER+" = ?";
		
		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_REF_NUMBER).equals(refNumber))
				return true;
			else
				return false;
		};
		
		try
		{
			return template.query(query, extractor, refNumber);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daofeedback.isexistingrefnumber-01");
		}
	}
	
	
	/**
	 * Generate a 7-digit hash, for reference in <strong>feedbackinfo</strong> table.
	 * 
	 * @return <strong>String of 7-digit reference number</strong>.
	 */
	private String generateRefNumber()
	{
		Random random = new Random();
		String refNumber;
		
		refNumber = String.valueOf(random.nextInt(1, 10));
		
		for (int i=0; i<6; i++)
			refNumber += String.valueOf(random.nextInt(0, 10));
		
		return refNumber;
	}

}
