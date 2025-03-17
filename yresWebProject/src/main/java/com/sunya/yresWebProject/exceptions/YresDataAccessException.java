package com.sunya.yresWebProject.exceptions;

import org.springframework.dao.DataAccessException;

public class YresDataAccessException extends DataAccessException {

	public YresDataAccessException(String msg) {
		super(msg);
	}

	public YresDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
