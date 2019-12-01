package org.mjjaenl.reactivetutorial.exception;

public class CustomException extends Throwable {
	private static final long serialVersionUID = 1076966838646040810L;
	private String message;
	
	public CustomException(Throwable e) {
		this.setMessage(e.getMessage());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
