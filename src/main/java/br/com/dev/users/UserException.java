package br.com.dev.users;

public class UserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8665152520754580009L;

    public UserException() {
    }

    public UserException(final String message) {
	super(message);
    }

    public UserException(Throwable cause) {
	super(cause);
    }

    public UserException(String message, Throwable cause) {
	super(message, cause);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
