package co.com.foundation.crud.jpa.exceptions;

import java.io.Serializable;

public class SystemException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}
}
