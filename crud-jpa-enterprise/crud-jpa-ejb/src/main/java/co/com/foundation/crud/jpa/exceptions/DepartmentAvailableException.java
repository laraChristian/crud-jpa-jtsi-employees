package co.com.foundation.crud.jpa.exceptions;

public class DepartmentAvailableException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public DepartmentAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public DepartmentAvailableException(String message) {
		super(message);
	}

}
