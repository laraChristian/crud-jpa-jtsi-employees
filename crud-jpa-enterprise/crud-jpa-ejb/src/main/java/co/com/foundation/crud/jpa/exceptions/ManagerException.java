package co.com.foundation.crud.jpa.exceptions;

public class ManagerException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public ManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerException(String message) {
		super(message);
	}
}
