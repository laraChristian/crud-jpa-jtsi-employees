package co.com.foundation.crud.jpa.domain;

import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmployeeRequest {

	private boolean update;
	private EmployeeDTO employee;

	public EmployeeRequest() {
		super();
	}
}
