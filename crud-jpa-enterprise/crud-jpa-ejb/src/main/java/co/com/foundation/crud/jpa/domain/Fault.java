package co.com.foundation.crud.jpa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Fault {

	private String code;
	private String type;
	private String message;
}
