package co.com.foundation.crud.jpa.exceptions;

public class DuplicateMailException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public DuplicateMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateMailException(String message) {
		super(message);
	}

}
