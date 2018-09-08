package co.com.foundation.crud.jpa.exceptions;

public class JobAvailableException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public JobAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public JobAvailableException(String message) {
		super(message);
	}

}
