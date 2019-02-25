package com.haohua.Exception;

public class DBAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBAccessException() {

	}

	public DBAccessException(String message) {
		super(message);
	}

	public DBAccessException(Throwable th) {
		super(th);
	}
}
