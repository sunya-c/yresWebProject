package com.sunya.daos;

public interface Dao
{
	/**
	 * <i>1. Specify database url, uname, and pass.</i><br>
	 * url = "...";<br>
	 * uname = "...";<br>
	 * pass = "...";
	 * <p>
	 * <p>
	 * <i>2. initiate the mysql driver.</i><br>
	 * Class.forName("com.mysql.cj.jdbc.Driver");
	 */
	void setupDbms();
}
