package com.sunya.yresWebProject.daos;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelDownloadinfo;

@Repository
public class DaoDownloadinfo
{
	private final String TABLE_NAME = "downloadinfo";
	private final String COLUMN_FILENAME = "filename";
	private final String COLUMN_FILEDATA = "filedata";

	@Autowired
	JdbcTemplate template;
	
	public ArrayList<ModelDownloadinfo> getNames()
	{
		String query = "SELECT "+COLUMN_FILENAME+" FROM "+TABLE_NAME+" ORDER BY "+COLUMN_FILENAME+" ASC;";
		
		ResultSetExtractor<ArrayList<ModelDownloadinfo>> extractor = rs -> {
			ArrayList<ModelDownloadinfo> models = new ArrayList<>();
			while (rs.next())
			{
				ModelDownloadinfo model = new ModelDownloadinfo();
				model.setFilename(rs.getString(COLUMN_FILENAME));
				models.add(model);
			}
			return models;
		};
		
		try
		{
			return template.query(query, extractor);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daodownloadinfo.getnames-01");
		}
		
	}
	
	public boolean doesExistFilename(String filename)
	{
		if (filename==null)
			return false;
		
		String query = "SELECT "+COLUMN_FILENAME+" FROM "+TABLE_NAME+" WHERE "+COLUMN_FILENAME+" = ?";
		
		ResultSetExtractor<Boolean> extractor = rs -> {
			if (rs.next() && rs.getString(COLUMN_FILENAME).equals(filename))
			{
				return true;
			}
			return false;
		};
		try
		{
			return template.query(query, extractor, filename);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daodownloadinfo.doesexistfilename-01");
		}
	}
	
	public ModelDownloadinfo getFile(String filename) throws SomethingWentWrongException
	{
		if (filename==null)
			throw new SomethingWentWrongException("Filename cannot be null.");
		
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_FILENAME+" = ?";
		
		ResultSetExtractor<ModelDownloadinfo> extractor = rs -> {
			ModelDownloadinfo model = null;
			if (rs.next() && rs.getString("filename").equals(filename))
			{
				model = new ModelDownloadinfo();
				model.setFilename(rs.getString("filename"));
				model.setFiledata(rs.getBinaryStream("filedata"));
			}
			return model;
		};
		ModelDownloadinfo model;
		try
		{
			model = template.query(query, extractor, filename);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daodownloadinfo.getfile-01");
		}
		if (model==null)
			throw new SomethingWentWrongException("Filename doesn't exist.");
		return model;
	}
	
	public void addFile(String filename, InputStream inStream)
	{
		if (filename==null || inStream==null)
			throw new YresDataAccessException("filename and data cannot be null");
		
		String query = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_FILENAME+", "+COLUMN_FILEDATA+") VALUES (?, ?);";
		
		PreparedStatementSetter setter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, filename);
				ps.setBlob(2, inStream);
			}
		};
		int total;
		try
		{
			total = template.update(query, setter);
		}
		catch (Exception e)
		{
			throw new YresDataAccessException("daodownloadinfo.addfile-01");
		}
		if (total!=1)
		{
			throw new YresDataAccessException("daodownloadinfo.addfile-02");
		}
	}
}
