package com.sunya.yresWebProject.daos;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelSiteUsage;

@Repository
public class DaoSiteUsage
{
	// tableName :
	private final String TABLE_NAME = "usageinfo";
	// end -- tableName

	// columnName :
	private final String COLUMN_REF_NUMBER = "reference_number"; // Primary key
	private final String COLUMN_IP = "ip_address";
	private final String COLUMN_DATE = "latest_interaction_date";

	private final String COLUMN_PAGE_CREATEACCOUNT = "page_createaccount";
	private final String COLUMN_PAGE_ERROR = "page_error";
	private final String COLUMN_PAGE_FEEDBACK = "page_feedback";
	private final String COLUMN_PAGE_LOGIN = "page_login";
	private final String COLUMN_PAGE_PERSINFO = "page_persinfo";
	private final String COLUMN_PAGE_REDIRECTING = "page_redirecting";
	private final String COLUMN_PAGE_UNDERCONSTRUCTION = "page_underconstruction";
	private final String COLUMN_PAGE_WELCOME = "page_welcome";
	private final String COLUMN_RESUME_DOWNLOAD = "resume_download";
	private final String COLUMN_PAGE_RESTAPI = "page_restapi";
	// end -- columnName

	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;

	@Autowired
	private JdbcTemplate template;


	/**
	 * Increment the <strong>whichPage</strong>'s usage counter of the given
	 * refNumber on the database by 1.<br>
	 * <br>
	 * If refNumber is null, this method will create a new refNumber on the
	 * database.
	 * 
	 * @param refNumber ~ refNumber to increment the counter, enter {@code null} to
	 *                  create new refNumber.
	 * @param whichPage ~ The page to increment the counter.
	 * @return <strong>String refNumber</strong>. It's the same refNumber as the
	 *         parameter refNumber. If the parameter refNumber is null, it returns a
	 *         newly-created refNumber.
	 */
	public String incrementCounter(String refNumber, PageUsageinfo whichPage)
	{
		Assert.notNull(whichPage, "whichPage must not be null.");

		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_DATE+" = ?, "+whichPage.getColumnName()+" = "
									+whichPage.getColumnName()+" + ? "+"WHERE "+COLUMN_REF_NUMBER+" = ?;";

		if (refNumber==null)
		{
			do
			{
				refNumber = generateRefNumber();
			}
			while (doesExistRefNumber(refNumber));

			addRefNumber(refNumber);
		}

		ModelSiteUsage model = new ModelSiteUsage();

		TimeZone timeZone = TimeZone.getDefault();
		LocalDateTime dateTime = LocalDateTime.now().minus(timeZone.getRawOffset(), ChronoUnit.MILLIS).plusHours(7);
		String updatedDateTime = dateTime.format(dateTimeFormat);

		model.setDateTime(updatedDateTime);
		model.setUsageValue(1, whichPage); // Add 1 to the existing value.
		model.setRefNumber(refNumber);

		int row;
		try
		{
			row = template.update(query, model.getDateTime(), model.getUsageValue(whichPage), model.getRefNumber());
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.increasecounter-01");
		}

		if (row==1)
			return refNumber;
		else
			throw new YresDataAccessException("daositeusage.increasecounter-02");

	}


	/**
	 * Fetch the site usage counter of all pages from the database and return it in
	 * a ModelSiteUsage format.
	 * 
	 * @param refNumber ~ This method fetches usage counter of this refNumber.
	 * @return <strong>ModelSiteUsage model</strong> ~ The model that contains the
	 *         results (included data: page_createaccount, page_error,
	 *         page_feedback, page_login, page_persinfo, page_redirecting,
	 *         page_underconstruction, page_welcome, resume_download).<br>
	 *         <strong>null</strong> ~ if refNumber doesn't exist in the database.
	 */
	public ModelSiteUsage getUsage(String refNumber)
	{
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REF_NUMBER+" = ?;";

		ResultSetExtractor<ModelSiteUsage> extractor = rs -> {
			if (rs.next())
			{
				ModelSiteUsage model = new ModelSiteUsage();
				model.setUsageValue(rs.getInt(COLUMN_PAGE_CREATEACCOUNT), PageUsageinfo.PAGE_CREATEACCOUNT);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_ERROR), PageUsageinfo.PAGE_ERROR);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_FEEDBACK), PageUsageinfo.PAGE_FEEDBACK);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_LOGIN), PageUsageinfo.PAGE_LOGIN);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_PERSINFO), PageUsageinfo.PAGE_PERSINFO);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_REDIRECTING), PageUsageinfo.PAGE_REDIRECTING);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_UNDERCONSTRUCTION), PageUsageinfo.PAGE_UNDERCONSTRUCTION);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_WELCOME), PageUsageinfo.PAGE_WELCOME);
				model.setUsageValue(rs.getInt(COLUMN_RESUME_DOWNLOAD), PageUsageinfo.RESUME_DOWNLOAD);
				model.setUsageValue(rs.getInt(COLUMN_PAGE_RESTAPI), PageUsageinfo.PAGE_RESTAPI);
				return model;
			}
			else
				return null;
		};

		try
		{
			return template.query(query, extractor, refNumber);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.getusage-01");
		}
	}


	/**
	 * Add a refNumber to the database.
	 * 
	 * @param refNumber ~ refNumber to be added.
	 */
	private void addRefNumber(String refNumber)
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_REF_NUMBER+") VALUES (?);";

		int row;
		try
		{
			row = template.update(query, refNumber);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.addrefnumber-01");
		}

		if (row!=1)
			throw new YresDataAccessException("daositeusage.addrefnumber-02");
	}


	/**
	 * Check in the database if the given refNumber exists.
	 * 
	 * @param refNumber ~ refNumber to be checked.
	 * @return <strong>true</strong> ~ if the given refNumber exists in the
	 *         database.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean doesExistRefNumber(String refNumber)
	{
		String query = "SELECT "+COLUMN_REF_NUMBER+" FROM "+TABLE_NAME+" WHERE "+COLUMN_REF_NUMBER+" = ?;";

		ResultSetExtractor<Boolean> extractor = (ResultSet rs) -> {
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
			throw new YresDataAccessException("daositeusage.isexistingrefnumber-01");
		}
	}


	/**
	 * Generate a 10-digit hash, for reference in <strong>usageinfo</strong> table.
	 * 
	 * @return <strong>String of 10-digit reference number</strong>
	 */
	private String generateRefNumber()
	{
		Random random = new Random();
		String newRefNumber = String.valueOf(random.nextInt(1, 10));

		for (int i = 0; i<9; i++)
			newRefNumber += String.valueOf(random.nextInt(0, 10));

		return newRefNumber;
	}


	/**
	 * Add a client's IP address to the database.
	 * 
	 * @param ip        ~ The client's IP address to be added.
	 * @param refNumber ~ The client's reference number to add the IP address to.
	 */
	public void addIP(String ip, String refNumber)
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_IP+" = ? WHERE "+COLUMN_REF_NUMBER+" = ?;";

		ModelSiteUsage model = new ModelSiteUsage();
		model.setIp(ip);
		model.setRefNumber(refNumber);

		int row;
		try
		{
			row = template.update(query, model.getIp(), model.getRefNumber());
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.addip-01");
		}

		if (row!=1)
			throw new YresDataAccessException("daositeusage.addip-02");
	}


	/**
	 * Return an IP address of a refNumber. Return null if there's no IP address
	 * information for this refNumber or refNumber doesn't exist.
	 * 
	 * @param refNumber ~ This method returns the IP address of this reference
	 *                  number.
	 * @return <strong>String IP address</strong> ~ of the given reference
	 *         number.<br>
	 *         <strong>null</strong> ~ if there's no IP address information for this
	 *         refNumber or refNumber doesn't exist.
	 */
	public String getIP(String refNumber)
	{
		String query = "SELECT "+COLUMN_REF_NUMBER+", "+COLUMN_IP+" FROM "+TABLE_NAME+" WHERE "+COLUMN_REF_NUMBER
									+" = ?;";

		ResultSetExtractor<String> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_REF_NUMBER).equals(refNumber))
				return rs.getString(COLUMN_IP);
			else
				return null;
		};

		try
		{
			return template.query(query, extractor, refNumber);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.getip-01");
		}
	}


	/**
	 * Remove rows from usageinfo table where IP address is in blacklist.
	 * 
	 * @param blacklistedIPs ~ Get these IPs from DaoIPBlacklist.getIPs().
	 * @param usageIPs       ~ Get these IPs from DaoSiteUsage.getIPs();
	 */
	public void removeUsageByBlacklist(ArrayList<String> blacklistedIPs, ArrayList<String> usageIPs)
	{
		String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_IP+" = ?;";

		try
		{
			int row = 0;
			for (int i = 0; i<blacklistedIPs.size(); i++)
			{
				if (usageIPs.contains(blacklistedIPs.get(i)))
					row += template.update(query, blacklistedIPs.get(i));
			}
			System.out.println("row deleted: "+row);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.removeusagebyblacklist-01");
		}
	}


	/**
	 * Get all <strong>IP addresses</strong> from the database.
	 * 
	 * @return <strong>ArrayList&lt;String&gt; of all IP addresses</strong> in the usageinfo table.
	 */
	public ArrayList<String> getIPs()
	{
		String query = "SELECT "+COLUMN_IP+" FROM "+TABLE_NAME+";";

		RowMapper<String> rowMapper = (ResultSet rs, int rowNum) -> rs.getString(COLUMN_IP);

		try
		{
			return (ArrayList<String>)template.query(query, rowMapper);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daositeusage.getips-01");
		}
	}

}
