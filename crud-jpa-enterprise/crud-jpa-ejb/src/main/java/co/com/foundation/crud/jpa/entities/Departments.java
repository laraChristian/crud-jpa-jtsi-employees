package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Departments")
@NamedQueries({
		@NamedQuery(name = "Departments.listAll", query = "SELECT new co.com.foundation.crud.jpa.dto.DepartmentDTO(d.departmentId, d.departmentName, l.locationId, l.city, m.employeeId, m.firstName, m.lastName )"
				+ " FROM Departments d LEFT JOIN d.locationId l LEFT JOIN d.managerId m  ORDER BY d.departmentName"),
		@NamedQuery(name = "Departments.isAvailable", query = "SELECT COUNT(d.managerId) FROM  Departments d WHERE d.departmentId = :id"),
		@NamedQuery(name = "Departments.getByManager", query = "SELECT new co.com.foundation.crud.jpa.dto.DepartmentDTO(d.departmentId, d.departmentName, l.locationId) FROM Departments d "
				+ "JOIN d.locationId l JOIN  d.managerId m WHERE m.employeeId = :managerId"),
		@NamedQuery(name = "Departments.exist", query = "SELECT COUNT(d) FROM Departments d WHERE d.departmentId = :id"),
		@NamedQuery(name = "Departments.haveEmployees", query = "SELECT COUNT(d) FROM Employees e JOIN e.department d WHERE d.departmentId = :id") })
@XmlRootElement(name = "department")
@XmlAccessorType(XmlAccessType.FIELD)
public class Departments implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DEPARTMENTS_SEQ", sequenceName = "DEPARTMENTS_SEQ")
	@Column(name = "DEPARTMENT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENTS_SEQ")
	@XmlAttribute
	private Long departmentId;

	@Column(name = "DEPARTMENT_NAME", length = 30, nullable = false, unique = false)
	@XmlElement
	private String departmentName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false, unique = false, insertable = true, updatable = true)
	@XmlElement
	private Employees managerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID", nullable = true, unique = false, insertable = true, updatable = true)
	@XmlElement
	private Locations locationId;

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.Employees.class, fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.MERGE)
	@XmlElement
	private Set<Employees> employeesDepartmentsViaDepartmentId = new HashSet<Employees>();

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.JobHistory.class, fetch = FetchType.LAZY, mappedBy = "departmentId", cascade = CascadeType.REMOVE)
	@XmlElement
	private Set<JobHistory> jobHistoryDepartmentsViaDepartmentId = new HashSet<JobHistory>();

	public Departments() {
		super();
	}

}
