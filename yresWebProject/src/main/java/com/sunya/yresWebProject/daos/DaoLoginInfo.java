package com.sunya.yresWebProject.daos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelLoginInfo;

@Repository
@Primary
public class DaoLoginInfo extends Dao
{
	// Table name :
	protected final String TABLE_NAME = "logininfo";
	
	
	// columnName :
	protected final String COLUMN_USERNAME = "webuname";  //Primary key
	protected final String COLUMN_PASSWORD = "webpass";
	protected final String COLUMN_TEMPACCOUNT = "tempaccount";
	protected final String COLUMN_TIMECREATED = "timecreated";
	// end -- columnName
	
	// Error report :
	private final String ERR1 = "Username already exists";
	private final String ERR2 = "Invalid username";
	private final String ERR3 = "Incorrect password";
	// end -- Error report
	
	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;
	
	@Autowired
	protected JdbcTemplate template;
	
	//TODO: Just removed the isExistingUsername part, have to do that in the upper layer
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
	 * @throws SomethingWentWrongException 
	 */
	public void removeUser(ModelLoginInfo model) throws SomethingWentWrongException
	{
		String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ? AND "+COLUMN_TEMPACCOUNT+" = ?";
		
		model.setTempaccount("1");
		
		int row;
		try
		{
			row = template.update(query, model.getUsername(), model.getPassword(), model.getTempaccount());  // 1==non-admin account. Admin account cannot be removed.
		}
		catch (DataAccessException e)
		{
			if (isExistingUsername(model))
				throw new SomethingWentWrongException("daologininfo.removeuser-01: Username not found");
			else
				throw new YresDataAccessException("daologininfo.removeuser-02");
		}
		
		if (row != 1)
			throw new YresDataAccessException("daologininfo.removeuser-03");
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
	 * @throws SomethingWentWrongException 
	 */
	public void addUser(ModelLoginInfo model) throws SomethingWentWrongException
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_USERNAME+", "+COLUMN_PASSWORD+", "+COLUMN_TIMECREATED+") VALUES (?, ?, ?);";
		
		TimeZone timeZone = TimeZone.getDefault();
		LocalDateTime dateTime = LocalDateTime.now()
				.minus(timeZone.getRawOffset(), ChronoUnit.MILLIS) // minus an offset (time zone of this local machine) to make it GMT+0.
				.plusHours(7);                                     // converting local machine's time to GMT+7
		String createTime = dateTime.format(dateTimeFormat);
		
		int row;
		try
		{
			row = template.update(query, model.getUsername(), model.getPassword(), createTime);
		}
		catch (DataAccessException e)
		{
			if (isExistingUsername(model))
				throw new SomethingWentWrongException("daologininfo.adduser-01");
			else
				throw new YresDataAccessException("daologininfo.adduser-02");
		}
		
		if (row != 1)
			throw new YresDataAccessException("daologininfo.adduser-03");
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
	 */
	public boolean isExistingUsername(ModelLoginInfo model)
	{
		// For username checking
		String query = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";
		
		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next())  // NOT case-sensitive
				return true;
			else
				return false;
		};
		
		try
		{
			return template.query(query, extractor, model.getUsername());
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daologininfo.isexistingusername-01");
		}
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
	 */
	public boolean isExistingUsernameCaseSen(ModelLoginInfo model)
	{
		// For username checking
		String query = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";
		
		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_USERNAME).equals(model.getUsername()))  // case-sensitive
				return true;
			else
				return false;
		};
		
		try
		{
			return template.query(query, extractor, model.getUsername());
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daologininfo.isexistingusernamecasesen-01");
		}
		
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
	 */
	public boolean isExistingPasswordCaseSen(ModelLoginInfo model)
	{
		// For password checking
		String query = "SELECT "+COLUMN_USERNAME+", "+COLUMN_PASSWORD+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ?;";
		
		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_USERNAME).equals(model.getUsername()) && rs.getString(COLUMN_PASSWORD).equals(model.getPassword()))
				return true;
			else
				return false;
		};
		
		try
		{
			return template.query(query, extractor, model.getUsername(), model.getPassword());
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daologininfo.isexistingpasswordcasesen-01");
		}
	}

	/**
	 * 
	 * @param username
	 * <p>
	 * @return true ~ if the username exists regardLESS of upper or lower case.
	 * <p>     false ~ otherwise.
	 */
	public boolean checkUsername(ModelLoginInfo model)
	{
		if (isExistingUsername(model))
			return true;
		else
		{
			PrintError.println(ERR2); // Invalid username
			return false;
		}
	}
	
	/**
	 * 
	 * @param username
	 * <p>
	 * @return true ~ if the username case-sensitively exists.
	 * <p>     false ~ otherwise.
	 */
	public boolean checkUsernameCaseSen(ModelLoginInfo model)
	{
		if (isExistingUsernameCaseSen(model))
			return true;
		else
		{
			PrintError.println(ERR2); // Invalid username
			return false;
		}
	}

	public boolean checkPasswordCaseSen(ModelLoginInfo model)
	{
		if (isExistingPasswordCaseSen(model))
			return true;
		else
		{
			PrintError.println(ERR3); // Incorrect password
			return false;
		}
	}

}
