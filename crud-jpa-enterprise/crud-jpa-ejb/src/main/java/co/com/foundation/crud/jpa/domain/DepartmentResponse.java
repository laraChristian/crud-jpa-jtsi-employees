package co.com.foundation.crud.jpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@XmlRootElement(name = "department-response")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentResponse {

	@XmlElement
	private List<DepartmentDTO> departmentsDTO;
	@XmlElement
	private String message;
	@XmlElement
	private boolean success;

	public DepartmentResponse() {
		super();
	}

}
