package co.com.foundation.crud.jpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@XmlRootElement(name = "employee-response")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeResponse {

	@XmlElement
	private String message;
	@XmlElement
	private String cause;
	@XmlElement
	private List<EmployeeDTO> employeesDTO;
	@XmlElement
	private boolean success;

	public EmployeeResponse() {
		super();
	}
}
