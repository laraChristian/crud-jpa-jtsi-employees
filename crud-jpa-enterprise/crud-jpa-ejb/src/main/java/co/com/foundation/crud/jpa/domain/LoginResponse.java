package co.com.foundation.crud.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {

	private boolean loggedIn;
	private User user;
	private String message;

	public LoginResponse() {
		super();
	}
}
