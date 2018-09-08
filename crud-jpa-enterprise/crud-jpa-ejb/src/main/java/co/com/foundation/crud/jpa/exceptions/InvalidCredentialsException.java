package co.com.foundation.crud.jpa.exceptions;

public class InvalidCredentialsException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public InvalidCredentialsException(String message) {
		super(message);
	}

}
