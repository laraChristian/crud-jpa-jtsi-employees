package co.com.foundation.crud.jpa.exceptions;

public class EmployeeNotExistException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeNotExistException(String message) {
		super(message);
	}

}
