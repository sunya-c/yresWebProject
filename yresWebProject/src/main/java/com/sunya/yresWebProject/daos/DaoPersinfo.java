package com.sunya.yresWebProject.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.models.ModelPersinfo;

@Repository
public class DaoPersinfo
{
	private final String TABLE_PERSINFO = "persinfo";
	// primary key
	private final String COLUMN_PERSINFO_ID = "persinfo_id";
	private final String COLUMN_FIRSTNAME = "firstname";
	private final String COLUMN_LASTNAME = "lastname";
	private final String COLUMN_DATEOFBIRTH = "dateofbirth";
	private final String COLUMN_GENDER = "gender";
	private final String COLUMN_NATIONALITY = "nationality";
	private final String COLUMN_DRIVINGLICENSE = "driving_lc";
	private final String COLUMN_ENGINEERINGlICENSE = "engineering_lc";
	private final String COLUMN_ADDRESS = "address";
	private final String COLUMN_PHONENUMBER = "phone_number";
	private final String COLUMN_EMAIL = "email";
	private final String COLUMN_LINEID = "lineid";
	
	// persinfo_language and persinfo_programminglanguage
	private final String TABLE_LANGUAGE = "persinfo_language";
	private final String TABLE_PROGRAMMINGLANGUAGE = "persinfo_programminglanguage";
	private final String COLUMN_LANGUAGE = "language";
	private final String COLUMN_PROFICIENCY = "proficiency";
	
	// persinfo_education
	private final String TABLE_EDUCATION = "persinfo_education";
	private final String COLUMN_LINE1 = "line1";
	private final String COLUMN_LINE2 = "line2";
	private final String COLUMN_LINE3 = "line3";
	private final String COLUMN_LINE4 = "line4";
	
	// persinfo_englishtest
	private final String TABLE_ENGLISHTEST = "persinfo_englishtest";
	private final String COLUMN_TESTNAME = "testname";
	private final String COLUMN_SCORE1 = "score1";
	private final String COLUMN_SCORE2 = "score2";
	private final String COLUMN_SCORE3 = "score3";
	private final String COLUMN_SCORE4 = "score4";
	
	@Autowired
	private JdbcTemplate template;
	@Autowired
	@Qualifier("frontDate")
	private DateTimeFormatter dateFormatFront;
	@Autowired
	@Qualifier("backDate")
	private DateTimeFormatter dateFormatBack;
	
	
	public ModelPersinfo getPersinfoMinimal(int persinfo_id)
	{
		return getPersinfoMinimalPrivate(persinfo_id, false);
	}
	
	public ModelPersinfo getPersinfoFullVersion(int persinfo_id)
	{
		ModelPersinfo model = getPersinfoMinimalPrivate(persinfo_id, true);
		
		if (model!=null)
		{
			addEducationAddOn(model);
			addEnglishTestAddOn(model);
		}
		return model;
	}
	
	private ModelPersinfo getPersinfoMinimalPrivate(int persinfo_id, boolean includeAddress)
	{
		String sql = 
		"SELECT "
		+	COLUMN_PERSINFO_ID+", "
		+ 	COLUMN_FIRSTNAME+", "
		+ 	COLUMN_LASTNAME+", "
		+ 	COLUMN_DATEOFBIRTH+", "
		+ 	COLUMN_GENDER+", "
		+ 	COLUMN_NATIONALITY+", "
		+ 	COLUMN_DRIVINGLICENSE+", "
		+ 	COLUMN_ENGINEERINGlICENSE+", "
		+	((includeAddress)? COLUMN_ADDRESS+", " : "")
		+ 	COLUMN_PHONENUMBER+", "
		+ 	COLUMN_EMAIL+", "
		+ 	COLUMN_LINEID
		+ " FROM "+TABLE_PERSINFO+" WHERE "+COLUMN_PERSINFO_ID+" = ?";
		
		ResultSetExtractor<ModelPersinfo> extractor = new ResultSetExtractor<ModelPersinfo>() {
			
			@Override
			public ModelPersinfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				if (rs.next())
				{
					ModelPersinfo model = new ModelPersinfo();
					model.setId(rs.getInt(COLUMN_PERSINFO_ID));
					model.setFirstname(rs.getString(COLUMN_FIRSTNAME));
					model.setLastname(rs.getString(COLUMN_LASTNAME));
					LocalDate dateOfBirth = LocalDate.parse(rs.getString(COLUMN_DATEOFBIRTH), dateFormatBack);
					model.setDateOfBirth(dateOfBirth.format(dateFormatFront));
					model.setGender(rs.getString(COLUMN_GENDER));
					model.setNationality(rs.getString(COLUMN_NATIONALITY));
					model.setDrivingLicense((rs.getBoolean(COLUMN_DRIVINGLICENSE))? "Yes":"No");
					model.setEngineeringLicense((rs.getBoolean(COLUMN_ENGINEERINGlICENSE))? "Yes":"No");
					if (includeAddress) model.setAddress(rs.getString(COLUMN_ADDRESS));
					model.setPhoneNumber(rs.getString(COLUMN_PHONENUMBER));
					model.setEmail(rs.getString(COLUMN_EMAIL));
					model.setLineid(rs.getString(COLUMN_LINEID));
					return model;
				}
				return null;
			}
		};
		
		ModelPersinfo model = template.query(sql, extractor, persinfo_id);
		if (model!=null)
		{
			addLanguageAddOn(model);
			addProgrammingLanguageAddOn(model);
		}
		return model;
	}
	
	/**
	 * Add languages from the database to the given model,
	 * noting that data will be set to the model, so this method
	 * returns nothing.
	 * 
	 * @param model ~ languages are added to this model.
	 */
	private void addLanguageAddOn(ModelPersinfo model)
	{
		String sql =
		"SELECT "
		+ 	COLUMN_LANGUAGE+", "
		+ 	COLUMN_PROFICIENCY
		+ " FROM "+TABLE_LANGUAGE+" WHERE "+COLUMN_PERSINFO_ID+" = ?";
		
		ResultSetExtractor<ModelPersinfo> extractor = new ResultSetExtractor<ModelPersinfo>() {
			
			@Override
			public ModelPersinfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				while (rs.next())
				{
					model.addLanguage(
								rs.getString(COLUMN_LANGUAGE), rs.getString(COLUMN_PROFICIENCY));
				}
				return model;
			}
		};
		
		template.query(sql, extractor, model.getId());
	}
	
	/**
	 * Add programming languages from the database to the given model,
	 * noting that data will be set to the model, so this method
	 * returns nothing.
	 * 
	 * @param model ~ programming languages are added to this model.
	 */
	private void addProgrammingLanguageAddOn(ModelPersinfo model)
	{
		String sql =
		"SELECT "
		+ 	COLUMN_LANGUAGE+", "
		+ 	COLUMN_PROFICIENCY
		+ " FROM "+TABLE_PROGRAMMINGLANGUAGE+" WHERE "+COLUMN_PERSINFO_ID+" = ?";
		
		ResultSetExtractor<ModelPersinfo> extractor = new ResultSetExtractor<ModelPersinfo>() {
			
			@Override
			public ModelPersinfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				while (rs.next())
				{
					model.addProgrammingLanguage(
								rs.getString(COLUMN_LANGUAGE), rs.getString(COLUMN_PROFICIENCY));
				}
				return model;
			}
		};
		
		template.query(sql, extractor, model.getId());
	}
	
	private void addEducationAddOn(ModelPersinfo model)
	{
		String sql =
		"SELECT "
		+ 	COLUMN_LINE1+", "
		+ 	COLUMN_LINE2+", "
		+ 	COLUMN_LINE3+", "
		+ 	COLUMN_LINE4
		+ " FROM "+TABLE_EDUCATION+" WHERE "+COLUMN_PERSINFO_ID+" = ?";
		
		ResultSetExtractor<ModelPersinfo> extractor = new ResultSetExtractor<ModelPersinfo>() {
			
			@Override
			public ModelPersinfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				while (rs.next())
				{
					model.addEducation(
									rs.getString(COLUMN_LINE1), 
									rs.getString(COLUMN_LINE2), 
									rs.getString(COLUMN_LINE3), 
									rs.getString(COLUMN_LINE4));
				}
				return model;
			}
		};
		
		template.query(sql, extractor, model.getId());
	}
	
	private void addEnglishTestAddOn(ModelPersinfo model)
	{
		String sql =
		"SELECT "
		+ 	COLUMN_TESTNAME+", "
		+ 	COLUMN_SCORE1+", "
		+ 	COLUMN_SCORE2+", "
		+ 	COLUMN_SCORE3+", "
		+	COLUMN_SCORE4
		+ " FROM "+TABLE_ENGLISHTEST+" WHERE "+COLUMN_PERSINFO_ID+" = ?";
		
		ResultSetExtractor<ModelPersinfo> extractor = new ResultSetExtractor<ModelPersinfo>() {
			
			@Override
			public ModelPersinfo extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				while (rs.next())
				{
					model.addEnglishTest(
									rs.getString(COLUMN_TESTNAME), 
									rs.getString(COLUMN_SCORE1), 
									rs.getString(COLUMN_SCORE2), 
									rs.getString(COLUMN_SCORE3), 
									rs.getString(COLUMN_SCORE4));
				}
				return model;
			}
		};
		
		template.query(sql, extractor, model.getId());
	}
}