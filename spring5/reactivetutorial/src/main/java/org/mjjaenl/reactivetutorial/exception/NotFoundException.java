package org.mjjaenl.reactivetutorial.exception;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 2997511841670258982L;
	
	public NotFoundException(Throwable e) {
		super(e);
	}
	
	public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Exception e) {
        super(msg + " because of " + e.toString());
    }
}
