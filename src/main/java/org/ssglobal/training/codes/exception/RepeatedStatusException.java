package org.ssglobal.training.codes.exception;

public class RepeatedStatusException extends Exception {
	private static final long serialVersionUID = -1068322544171870893L;
	private String message = "No repeated Process/On-going status Exception Error";

	public RepeatedStatusException() {
	}

	public RepeatedStatusException(String newMessage) {
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
