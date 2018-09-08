package co.com.foundation.crud.jpa.exceptions;

public class DuplicateRegionException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public DuplicateRegionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateRegionException(String message) {
		super(message);
	}

}
