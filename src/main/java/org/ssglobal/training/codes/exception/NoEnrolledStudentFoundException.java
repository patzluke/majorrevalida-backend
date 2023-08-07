package org.ssglobal.training.codes.exception;

public class NoEnrolledStudentFoundException extends Exception{
	private static final long serialVersionUID = -6097808979528107661L;
	private String message = "No student enrolled found";
	
	public NoEnrolledStudentFoundException() {
	}
	
	public NoEnrolledStudentFoundException(String newMessage) {
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
