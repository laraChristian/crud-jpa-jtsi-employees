package co.com.foundation.crud.jpa.exceptions;

public class DuplicateJobException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public DuplicateJobException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateJobException(String message) {
		super(message);
	}

}
