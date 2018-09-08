package co.com.foundation.crud.jpa.domain;

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
@XmlRootElement(name = "department-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentRequest {

	@XmlElement
	private boolean update;
	@XmlElement
	private DepartmentDTO departmentDTO;

	public DepartmentRequest() {
		super();
	}

}
