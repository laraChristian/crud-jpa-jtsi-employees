package co.com.foundation.crud.jpa.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "employee-dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDTO {

	@XmlAttribute
	private long id;
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;
	@XmlElement
	private String email;
	@XmlElement
	private String identification;
	@XmlElement
	private String phoneNumber;
	@XmlElement
	private Date hireDate;
	@XmlElement
	private long salary;
	@XmlElement
	private Long commissionPct;
	@XmlElement
	private long departmentId;
	@XmlElement
	private String departmentName;
	@XmlElement
	private long managerId;
	@XmlElement
	private String managerName;
	@XmlElement
	private String jobId;
	@XmlElement
	private String jobTitle;

	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Long id, String firstName, String lastName, Long departmentId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentId = departmentId;
	}

	public EmployeeDTO(Long id, String firstName, String lastName, long departmentId, String jobId, String jobTitle) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentId = departmentId;
		this.jobId = jobId;
		this.jobTitle = jobTitle;
	}

	public EmployeeDTO(long id, String firstName, String lastName, String email, String identification,
			String phoneNumber, Date hireDate, long salary, Long commissionPct, long departmentId,
			String departmentName, long managerId, String managerName, String jobId, String jobTitle) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.identification = identification;
		this.phoneNumber = phoneNumber;
		this.hireDate = hireDate;
		this.salary = salary;
		this.commissionPct = commissionPct;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.managerId = managerId;
		this.managerName = managerName;
		this.jobId = jobId;
		this.jobTitle = jobTitle;
	}

}
