package org.mjjaenl.reactivetutorial.exception;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 5852106947290535145L;

	public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String msg, Exception e) {
        super(msg + " because of " + e.toString());
    }
}
