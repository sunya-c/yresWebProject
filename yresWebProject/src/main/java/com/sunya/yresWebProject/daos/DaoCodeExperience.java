package com.sunya.yresWebProject.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.models.ModelCodeExperience;
import com.sunya.yresWebProject.models.ModelCodeExperienceDetail;
import com.sunya.yresWebProject.models.ModelCodeExperienceTechnology;

@Repository
public class DaoCodeExperience
{
	// Table name
	private final String TABLE_NAME = "tb_code_experience";
	private final String TABLE_NAME_DETAIL = "tb_code_experience_detail";
	private final String TABLE_NAME_TECHNOLOGY = "tb_code_experience_technology";

	// columnName
	private final String EXP_ID = "exp_id";
	private final String TITLE = "title";
	private final String FROM_MONTH = "from_month";
	private final String TO_MONTH = "to_month";
	private final String LINK = "link";
	private final String DETAIL_ID = "detail_id";
	private final String DETAIL = "detail";
	private final String TECH_TYPE = "type";
	private final String TECH_ID = "tech_id";
	private final String TECHNOLOGY = "technology";

	@Autowired
	private JdbcTemplate template;
	@Autowired
	@Qualifier("backDate")
	private DateTimeFormatter dateFormat;
	
	public List<ModelCodeExperience> getCodeExperienceModels()
	{
		String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+EXP_ID+";";
		
		ResultSetExtractor<List<ModelCodeExperience>> extractor = new ResultSetExtractor<List<ModelCodeExperience>>() {
			
			@Override
			public List<ModelCodeExperience> extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				LinkedList<ModelCodeExperience> models = new LinkedList<>();
				while (rs.next())
				{
					ModelCodeExperience model = new ModelCodeExperience();
					model.setExpId(rs.getInt(EXP_ID));
					model.setTitle(rs.getString(TITLE));
					model.setFromMonth(rs.getDate(FROM_MONTH).toLocalDate());
					model.setToMonth(rs.getDate(TO_MONTH)==null? null : rs.getDate(TO_MONTH).toLocalDate());
					model.setLink(rs.getString(LINK));
					models.add(model);
				}
				return models;
			}
		};
		
		return template.query(sql, extractor);
	}

	public ModelCodeExperience getModelCodeExperience()
	{
		return getModelCodeExperience(1);
	}
	
	public ModelCodeExperience getModelCodeExperience(int expId)
	{
		String sql = 
			" SELECT * FROM "+TABLE_NAME
			+" LEFT JOIN "+TABLE_NAME_DETAIL+" ON "+TABLE_NAME+"."+EXP_ID+" = "+TABLE_NAME_DETAIL+"."+EXP_ID
			+" LEFT JOIN "+TABLE_NAME_TECHNOLOGY+" ON "+TABLE_NAME+"."+EXP_ID+" = "+TABLE_NAME_TECHNOLOGY+"."+EXP_ID
			+" WHERE "+TABLE_NAME+"."+EXP_ID+" = ? "
			+" ORDER BY "+TABLE_NAME+"."+EXP_ID+", "+TABLE_NAME_DETAIL+"."+DETAIL_ID+", "+TABLE_NAME_TECHNOLOGY+"."+TECH_TYPE+", "+TABLE_NAME_TECHNOLOGY+"."+TECH_ID+";";
		
		ResultSetExtractor<ModelCodeExperience> extractor = new ResultSetExtractor<ModelCodeExperience>() {
			
			@Override
			public ModelCodeExperience extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				ModelCodeExperience model = new ModelCodeExperience();
				LinkedList<ModelCodeExperienceDetail> details = new LinkedList<>();
				LinkedList<ModelCodeExperienceTechnology> technologies = new LinkedList<>();
				
				if (rs.next() && expId == rs.getInt(EXP_ID))
				{
					model.setExpId(rs.getInt(EXP_ID));
					model.setTitle(rs.getString(TITLE));
					model.setFromMonth(rs.getDate(FROM_MONTH).toLocalDate());
					model.setToMonth(rs.getDate(TO_MONTH)==null? null : rs.getDate(TO_MONTH).toLocalDate());
					model.setLink(rs.getString(LINK));
				}
				
				do
				{
					ModelCodeExperienceDetail detail = new ModelCodeExperienceDetail();
					detail.setExpId(rs.getInt(EXP_ID));
					detail.setDetailId(rs.getInt(DETAIL_ID));
					detail.setDetail(rs.getString(DETAIL));
					details.add(detail);
					
					ModelCodeExperienceTechnology technology = new ModelCodeExperienceTechnology();
					technology.setExpId(rs.getInt(EXP_ID));
					technology.setType(rs.getString(TECH_TYPE));
					technology.setTechId(rs.getInt(TECH_ID));
					technology.setTechnology(rs.getString(TECHNOLOGY));
					technologies.add(technology);
				}
				while (rs.next());
				
				model.setDetails(
					details.stream().distinct()
					.collect(Collectors.toCollection(LinkedList::new))
				);
				model.setTechnologies(
					technologies.stream().distinct()
					.collect(Collectors.toCollection(LinkedList::new))
				);

				return model;
			}
		};
		
		return template.query(sql, extractor, expId);
	}
}
