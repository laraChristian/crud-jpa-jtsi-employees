package co.com.foundation.crud.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

	private Long id;
	private String email;
	private String password;

	public User() {
		super();
	}

}
