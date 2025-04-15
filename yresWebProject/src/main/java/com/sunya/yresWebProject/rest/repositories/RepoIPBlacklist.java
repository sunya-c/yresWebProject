package com.sunya.yresWebProject.rest.repositories;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.rest.repositories.models.ModelIPBlacklist;

@Repository
public class RepoIPBlacklist
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
	 * Get all <strong>IP addresses</strong> from the database.
	 * 
	 * @return <strong>ArrayList&lt;String&gt; of all IP addresses</strong> in the blacklist.
	 */
	public ArrayList<ModelIPBlacklist> getIPs()
	{
		String query = "SELECT "+COLUMN_IP+", "+COLUMN_COUNTRY+" FROM "+TABLE_NAME+" ORDER BY "+COLUMN_COUNT+" DESC";

		ResultSetExtractor<ArrayList<ModelIPBlacklist>> extractor = (rs) -> {
			if (rs.next())
			{
				ArrayList<ModelIPBlacklist> list = new ArrayList<>();
				do
				{
					ModelIPBlacklist model = new ModelIPBlacklist();
					model.setIpAddress(rs.getString(COLUMN_IP));
					model.setCountryCode(rs.getString(COLUMN_COUNTRY));
					list.add(model);
				} while (rs.next());
				return list;
			}
			return null;
		};

		return template.query(query, extractor);
	}
}
