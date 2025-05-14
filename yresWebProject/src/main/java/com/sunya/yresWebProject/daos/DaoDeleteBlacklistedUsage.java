package com.sunya.yresWebProject.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sunya.yresWebProject.PrintTime;

@Repository
public class DaoDeleteBlacklistedUsage
{
	// tableName :
	private final String TABLE_USAGE = "usageinfo";
	private final String TABLE_BLACKLIST = "ipblacklistinfo";
	// end -- tableName
	
	// columnName :
	private final String COLUMN_REF_NUMBER = "reference_number"; // Primary key
	private final String COLUMN_IP = "ip_address";
	// end -- columnName
	
	@Autowired
	private JdbcTemplate template;
	
	
	
	/**
	 * Remove items in usageinfo that exist in ipblacklistinfo.
	 */
	public int removeBlacklistedFromUsageinfo()
	{
		String query = 
		"DELETE FROM "+TABLE_USAGE+" WHERE "+COLUMN_IP+" IN ("
		+ "SELECT "+COLUMN_IP+" FROM "+TABLE_BLACKLIST
		+ ");";
		
		PrintTime.println("Removing blacklisted IPs from usageinfo...");
		int total = template.update(query);
		System.out.println("rows deleted: "+total);
		return total;
	}
	
	
	
}
