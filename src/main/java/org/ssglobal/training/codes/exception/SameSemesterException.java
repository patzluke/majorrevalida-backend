package org.ssglobal.training.codes.exception;

public class SameSemesterException extends RuntimeException  {
	private static final long serialVersionUID = 2127737608709534267L;
	private String message = "Cannot enroll student in the same semester.";

	public SameSemesterException() {
	}

	public SameSemesterException(String newMessage) {
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
