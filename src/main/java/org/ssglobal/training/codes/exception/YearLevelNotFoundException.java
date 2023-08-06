package org.ssglobal.training.codes.exception;

public class YearLevelNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = "Year level not found Error";

	public YearLevelNotFoundException() {
	}

	public YearLevelNotFoundException(String newMessage) {
		message = newMessage;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void printStackTrace() {
		System.err.println(message);
	}

}
