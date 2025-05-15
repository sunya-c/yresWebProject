package com.sunya.yresWebProject.daos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelWebdatainfo;

@Repository
public class DaoWebdatainfo
{
	// Table name
	private final String TABLE_NAME = "webdatainfo";

	// columnName
	private final String COLUMN_KEY = "key_webinfo";
	private final String COLUMN_VALUE = "value_webinfo";

	public static final String WEB_NOTE1 = "WEB_NOTE1";
	public static final String WEB_RESUME_DATE = "WEB_resumeDate";

	@Autowired
	private JdbcTemplate template;
	@Autowired
	@Qualifier("backDate")
	private DateTimeFormatter dateFormat;


	/**
	 * Fetch the value of the given keyName from webdatainfo table.
	 * 
	 * @param keyName ~ The key of the value to be fetched.
	 * @return <strong>ModelWebdatainfo model</strong> ~ The model that contains the
	 *         result (included data: value).
	 */
	public ModelWebdatainfo getWebinfo(String keyName)
	{
		String query = "SELECT "+COLUMN_VALUE+", "+COLUMN_KEY+" FROM "+TABLE_NAME+" WHERE "+COLUMN_KEY+" = ?";

		ResultSetExtractor<ModelWebdatainfo> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_KEY).equals(keyName))
			{
				ModelWebdatainfo model = new ModelWebdatainfo();
				model.setValue(rs.getString(COLUMN_VALUE));
				return model;
			}
			else
				return null;
		};

		try
		{
			return template.query(query, extractor, keyName);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daowebdatainfo.getwebinfo-01");
		}
	}
	
	public void updateWebinfo(String keyName, String value)
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_VALUE+" = ? WHERE "+COLUMN_KEY+" = ?;";
		
		try
		{
			template.update(query, value, keyName);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daowebdatainfo.updatewebinfo-01");
		}
	}
	
	public void addWebinfo(String keyName, String value)
	{
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_KEY+", "+COLUMN_VALUE+") VALUES (?, ?);";
		
		try
		{
			template.update(query, keyName, value);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daowebdatainfo.addwebinfo-01");
		}
	}
	
	public void setResumeDate(LocalDate resumeDate)
	{
		String query = "UPDATE "+TABLE_NAME+" SET "+COLUMN_VALUE+" = ? WHERE "+COLUMN_KEY+" = ?;";
		
		try
		{
			template.update(query, resumeDate.format(dateFormat), DaoWebdatainfo.WEB_RESUME_DATE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new YresDataAccessException("daowebdatainfo.setresumedate-01");
		}
	}
}
