package com.sunya.yresWebProject.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.ModelDownloadinfo;

@Repository
public class DaoDownloadinfo
{
	@Autowired
	JdbcTemplate template;
	
	public ModelDownloadinfo getFile(String filename) throws SomethingWentWrongException
	{
		String query = "SELECT * FROM downloadinfo WHERE filename = ?";
		
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
		
		ModelDownloadinfo model = template.query(query, extractor, filename);
		if (model==null)
			throw new SomethingWentWrongException("Filename doesn't exist.");
		return model;
	}
}
