package com.sunya.yresWebProject.rest.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.rest.repositories.models.ModelLoginInfo;

@Repository
@Primary
public class RepoLoginInfo
{
	// Table name :
	protected final String TABLE_NAME = "logininfo";

	// columnName :
	protected final String COLUMN_USERNAME = "webuname"; // Primary key
	protected final String COLUMN_PASSWORD = "webpass";
	protected final String COLUMN_TEMPACCOUNT = "tempaccount";
	protected final String COLUMN_TIMECREATED = "timecreated";
	// end -- columnName

	// Error report :
//	private final String ERR1 = "Username already exists";
//	private final String ERR2 = "Invalid username";
//	private final String ERR3 = "Incorrect password";
	// end -- Error report

	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;

	@Autowired
	protected JdbcTemplate template;


	// for REST api
	public List<ModelLoginInfo> getUsers()
	{
		String query = "SELECT * FROM "+TABLE_NAME;
		
		ResultSetExtractor<List<ModelLoginInfo>> extractor = rs -> {
			List<ModelLoginInfo> list = null;
			
			if (rs.next())
			{
				list = new ArrayList<>();
				do
				{
					ModelLoginInfo model = new ModelLoginInfo();
					model.setUsername(rs.getString(COLUMN_USERNAME));
					model.setTempaccount(rs.getString(COLUMN_TEMPACCOUNT));
					model.setTimecreated(rs.getString(COLUMN_TIMECREATED));
					list.add(model);
				} while (rs.next());
			}
				return list;
		};
		
		return template.query(query, extractor);
	}
	
	
	public ModelLoginInfo getUser(String username)
	{
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?";
		
		ResultSetExtractor<ModelLoginInfo> extractor = new ResultSetExtractor<ModelLoginInfo>() {
			
			@Override
			public ModelLoginInfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				if (rs.next() && rs.getString(COLUMN_USERNAME).equals(username))
				{
					ModelLoginInfo model = new ModelLoginInfo();
					model.setUsername(rs.getString(COLUMN_USERNAME));
					model.setTempaccount(rs.getString(COLUMN_TEMPACCOUNT));
					model.setTimecreated(rs.getString(COLUMN_TIMECREATED));
					return model;
				}
				return null;
			}
		};
		
		return template.query(query, extractor, username);
	}
	// end --- for REST api
	
	
	// TODO: Just removed the isExistingUsername part, have to do that in the upper
	// layer
	/**
	 * Remove all data of a particular username from the database (password
	 * required). The removed username MUST NOT be admin-privileged accounts
	 * 
	 * @param model ~ A model that contains the account to be removed (requires:
	 *              username and password).
	 * @throws SomethingWentWrongException
	 */
	public void removeUser(ModelLoginInfo model) throws SomethingWentWrongException
	{
		String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ? AND "+COLUMN_PASSWORD+" = ? AND "
									+COLUMN_TEMPACCOUNT+" = ?";

		model.setTempaccount("1"); // 1==non-admin account. Admin account cannot be removed.

		int row;
		try
		{
			row = template.update(query, model.getUsername(), model.getPassword(), model.getTempaccount());
		}
		catch (DataAccessException e)
		{
			if (doesExistUsername(model))
				throw new SomethingWentWrongException("daologininfo.removeuser-01: Username not found");
			else
				throw new YresDataAccessException("daologininfo.removeuser-02");
		}

		if (row!=1)
			throw new YresDataAccessException("daologininfo.removeuser-03");
	}


	/**
	 * Add the new username and password to the database.
	 * 
	 * @param model ~ A model that contains the account detail to be added
	 *              (requires: username and password).
	 * @throws SomethingWentWrongException
	 */
	public void addUser(ModelLoginInfo model) throws SomethingWentWrongException
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_USERNAME+", "+COLUMN_PASSWORD+", "+COLUMN_TIMECREATED
									+") VALUES (?, ?, ?);";

		TimeZone timeZone = TimeZone.getDefault();
		LocalDateTime dateTime = LocalDateTime.now().minus(timeZone.getRawOffset(), ChronoUnit.MILLIS).plusHours(7);
		String createTime = dateTime.format(dateTimeFormat);

		int row;
		try
		{
			row = template.update(query, model.getUsername(), model.getPassword(), createTime);
		}
		catch (DataAccessException e)
		{
			if (doesExistUsername(model))
				throw new SomethingWentWrongException("daologininfo.adduser-01");
			else
				throw new YresDataAccessException("daologininfo.adduser-02");
		}

		if (row!=1)
			throw new YresDataAccessException("daologininfo.adduser-03");
	}


	/**
	 * Check in the database if the given username exists (NOT case-sensitive).
	 * 
	 * @param model ~ The model that contains the username to be checked (requires:
	 *              username).
	 * @return <strong>true</strong> ~ if the username exists
	 *         <strong>regardless</strong> of upper and lower cases.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean doesExistUsername(ModelLoginInfo model)
	{
		// For username checking
		String query = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";

		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next()) // NOT case-sensitive
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
	 * 
	 * @param model ~ A model that contains the username to be checked (requires:
	 *              username).
	 * @return <strong>true</strong> ~ if the username case-sensitively exists.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean doesExistUsernameCaseSen(ModelLoginInfo model)
	{
		// For username checking
		String query = "SELECT "+COLUMN_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME+" = ?;";

		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_USERNAME).equals(model.getUsername())) // case-sensitive
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
	 * Check if the given username and password exist (case-sensitive for both
	 * username and password).<br>
	 * <br>
	 * It's Recommended to call {@code doesExistUsernameCaseSen()} first to check the existence of
	 * the username before calling this method, as this method will return false
	 * if the username doesn't exist, and you won't know whether that false is from username or password.
	 * <p>
	 * 
	 * @param model ~ A model that contains the detail to be checked (requires:
	 *              username and password).
	 * @return <strong>true</strong> ~ if BOTH username AND password
	 *         case-sensitively exist.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean doesExistPasswordCaseSen(ModelLoginInfo model)
	{
		// For password checking
		String query = "SELECT "+COLUMN_USERNAME+", "+COLUMN_PASSWORD+" FROM "+TABLE_NAME+" WHERE "+COLUMN_USERNAME
									+" = ? AND "+COLUMN_PASSWORD+" = ?;";

		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_USERNAME).equals(model.getUsername())
										&& rs.getString(COLUMN_PASSWORD).equals(model.getPassword()))
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
}
