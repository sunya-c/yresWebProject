package com.sunya.yresWebProject.daos;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.YresDataAccessException;

@Repository
public class DaoIPBlacklist
{
	// tableName :
	private final String TABLE_NAME = "ipblacklistinfo";

	// columnName :
	private final String COLUMN_IP = "ip_address"; // Primary key
	private final String COLUMN_COUNTRY = "country_code";
	private final String COLUMN_COUNT = "block_count";

	@Autowired
	private JdbcTemplate template;


	/**
	 * Check in the database if the given <strong>IP address</strong> exists.
	 * 
	 * @param ip ~ The IP address to be checked.
	 * @return <strong>true</strong> ~ if the given IP address exists in the database (in blacklist).<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean isBlacklisted(String ip)
	{
		String query = "SELECT "+COLUMN_IP+" FROM "+TABLE_NAME+" WHERE "+COLUMN_IP+" = ?;";

		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_IP).equals(ip))
				return true;
			else
				return false;
		};
		
		try
		{
			return template.query(query, extractor, ip);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daoipblacklist.isblacklisted-01");
		}
	}


	/**
	 * Add <strong>IP address</strong> and <strong>country code</strong> to the database.
	 * 
	 * @param ip ~ The IP address to be added.
	 * @param countryCode ~ The country code to be added.
	 */
	public void addToBlacklist(String ip, String countryCode)
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_IP+", "+COLUMN_COUNTRY+") VALUES (?, ?);";

		int row;
		try
		{
			row = template.update(query, ip, countryCode);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daoipblacklist.addtoblacklist-01");
		}

		if (row!=1)
			throw new YresDataAccessException("daoipblacklist.addtoblacklist-02");
	}


	/**
	 * Increment the counter of the given <strong>IP address</strong> by 1.
	 * 
	 * @param ip ~ The IP address to increment the counter.
	 */
	public void incrementCounter(String ip)
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_COUNT+" = "+COLUMN_COUNT+" + ? WHERE "+COLUMN_IP+" = ?;";
		
		int row;
		try
		{
			row = template.update(query, 1, ip); // increase the counter by 1
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daoipblacklist.increasecounter-01");
		}

		if (row!=1)
			throw new YresDataAccessException("daoipblacklist.increasecounter-02");
	}


	/**
	 * Get all <strong>IP addresses</strong> from the database.
	 * 
	 * @return <strong>ArrayList&lt;String&gt; of all IP addresses</strong> in the blacklist.
	 */
	public ArrayList<String> getIPs()
	{
		String query = "SELECT "+COLUMN_IP+" FROM "+TABLE_NAME+";";

		RowMapper<String> rowMapper = (rs, rowNum) -> rs.getString(COLUMN_IP);

		return (ArrayList<String>)template.query(query, rowMapper);
	}
}
