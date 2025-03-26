package com.sunya.yresWebProject.daos;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private JdbcTemplate template;


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
}
