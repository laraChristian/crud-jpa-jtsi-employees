package co.com.foundation.crud.jpa.exceptions;

public class RegionAvailableException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public RegionAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegionAvailableException(String message) {
		super(message);
	}

}
