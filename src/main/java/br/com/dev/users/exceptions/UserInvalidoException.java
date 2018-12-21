package br.com.dev.users.exceptions;

public class UserInvalidoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2200000139636678760L;

    public UserInvalidoException() {
    }

    public UserInvalidoException(final String message) {
	super(message);
    }

    public UserInvalidoException(Throwable cause) {
	super(cause);
    }

    public UserInvalidoException(String message, Throwable cause) {
	super(message, cause);
    }

    public UserInvalidoException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
