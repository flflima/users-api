package br.com.dev.users.exceptions;

/**
 * Exceção lançada ao encontrar algum campo com valor inválido
 * 
 * @author felipe
 *
 */
public class CampoInvalidoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8970040230870315963L;

    public CampoInvalidoException() {
    }

    public CampoInvalidoException(String message) {
	super(message);
    }

    public CampoInvalidoException(Throwable cause) {
	super(cause);
    }

    public CampoInvalidoException(String message, Throwable cause) {
	super(message, cause);
    }

    public CampoInvalidoException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
