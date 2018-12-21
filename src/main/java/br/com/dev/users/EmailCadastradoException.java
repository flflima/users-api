package br.com.dev.users;

public class EmailCadastradoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2730660760318300319L;

    public EmailCadastradoException() {
    }

    public EmailCadastradoException(final String message) {
	super(message);
    }

    public EmailCadastradoException(Throwable cause) {
	super(cause);
    }

    public EmailCadastradoException(String message, Throwable cause) {
	super(message, cause);
    }

    public EmailCadastradoException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
