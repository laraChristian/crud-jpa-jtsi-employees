package co.com.foundation.crud.jpa.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@XmlRootElement(name = "department-dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentDTO {

	@XmlAttribute
	private Long departmentId;
	@XmlElement
	private String departmentName;
	@XmlElement
	private Long locationId;
	@XmlElement
	private String locationName;
	@XmlElement
	private Long managerId;
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;

	public DepartmentDTO() {
		super();
	}

	public DepartmentDTO(Long departmentId, String departmentName, Long locationId) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.locationId = locationId;
	}

	public DepartmentDTO(Long departmentId, String departmentName) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}

}
