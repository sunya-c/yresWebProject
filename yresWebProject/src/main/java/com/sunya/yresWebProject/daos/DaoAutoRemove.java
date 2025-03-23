package com.sunya.yresWebProject.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.exceptions.YresDataAccessException;
import com.sunya.yresWebProject.models.ModelLoginInfo;

@Repository
public class DaoAutoRemove extends DaoLoginInfo
{
	@Autowired
	@Qualifier("backDateTime")
	private DateTimeFormatter dateTimeFormat;



	/**
	 * Delete the user which <strong>username</strong> AND <strong>password</strong>
	 * AND <strong>time created</strong> match the argument passed into this method.
	 * 
	 * @param username
	 * @param password
	 * @param timeCreated
	 * @return
	 * @throws Exception
	 */
	private void removeTempUser(ModelLoginInfo model)
	{
		String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_TEMPACCOUNT+" = ? AND "+COLUMN_USERNAME+" = ? AND "
									+COLUMN_PASSWORD+" = ? AND "+COLUMN_TIMECREATED+" = ?";

		model.setTempaccount("1");

		PreparedStatementSetter pss = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, model.getTempaccount());
				ps.setString(2, model.getUsername());
				ps.setString(3, model.getPassword());
				ps.setString(4, model.getTimecreated());
			}
		};

		int row;
		try
		{
			row = template.update(query, pss);
		}
		catch (DataAccessException e)
		{
			throw new YresDataAccessException("daoautoremove.removetempuser-01");
		}

		if (row!=1)
			throw new YresDataAccessException("daoautoremove.removetempuser-02");
	}



	/**
	 * Automatically delete the user <i>60</i> minutes after registration
	 * 
	 * @throws Exception
	 */
	public void autoRemoveTempUser()
	{
		String query = "SELECT "+COLUMN_USERNAME+", "+COLUMN_PASSWORD+", "+COLUMN_TIMECREATED+" FROM "+TABLE_NAME
									+" WHERE "+COLUMN_TEMPACCOUNT+" = ?";

		ResultSetExtractor<ArrayList<ModelLoginInfo>> extractor = new ResultSetExtractor<>() {

			@Override
			public ArrayList<ModelLoginInfo> extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				ArrayList<ModelLoginInfo> models = null;

				if (rs.next())
				{
					models = new ArrayList<>();

					do
					{
						if (rs.getString(COLUMN_TEMPACCOUNT).equals("1"))
						{
							ModelLoginInfo model = new ModelLoginInfo();
							model.setUsername(rs.getString(COLUMN_USERNAME));
							model.setPassword(rs.getString(COLUMN_PASSWORD));
							model.setTimecreated(rs.getString(COLUMN_TIMECREATED));
							models.add(model);
						}
					}
					while (rs.next());
				}

				return models;
			}
		};

		ArrayList<ModelLoginInfo> models = template.query(query, extractor);

		TimeZone timeZone = TimeZone.getDefault();

		LocalDateTime timeNow = LocalDateTime.now().minus(timeZone.getRawOffset(), ChronoUnit.MILLIS) // converting
																										// local
																										// machine's
																										// time to GMT+0
									.plusHours(7); // converting GMT+0 to GMT+7

		if (models!=null)
		{
			for (var i = 0; i<models.size(); i++)
			{
				LocalDateTime timeCreated = LocalDateTime.parse(models.get(i).getTimecreated(), dateTimeFormat);
				Duration duration = Duration.between(timeCreated, timeNow);

				if (duration.toMinutes()>=60)
				{
					if (isExistingPasswordCaseSen(models.get(i)))
					{
						removeTempUser(models.get(i));

						System.out.println(timeCreated);
						System.out.println(timeNow);
						System.out.println(duration.toMinutes());
					}
				}
			}
		}

	}
}
