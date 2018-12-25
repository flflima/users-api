package br.com.dev.users.exceptions;

/**
 * Exceção lançada quando uma sessão não for válida
 * 
 * @author felipe
 *
 */
public class SessaoInvalidaException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1677810746764617696L;

    public SessaoInvalidaException() {
    }

    public SessaoInvalidaException(String message) {
	super(message);
    }

    public SessaoInvalidaException(Throwable cause) {
	super(cause);
    }

    public SessaoInvalidaException(String message, Throwable cause) {
	super(message, cause);
    }

    public SessaoInvalidaException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
