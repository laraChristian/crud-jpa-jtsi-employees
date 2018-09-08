package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
@Entity(name = "Employees")
@NamedQueries({
		@NamedQuery(name = "Employees.findAll", query = "SELECT new co.com.foundation.crud.jpa.dto.EmployeeDTO(e.employeeId, e.firstName, e.lastName,"
				+ " e.email, e.identification, e.phoneNumber, e.hireDate, e.salary, e.commissionPct, d.departmentId, d.departmentName, m.employeeId, m.firstName,"
				+ " j.jobId, j.jobTitle) FROM Employees e INNER JOIN e.department d INNER JOIN e.manager m INNER JOIN e.job j"),
		@NamedQuery(name = "Employees.listCMB", query = "SELECT new co.com.foundation.crud.jpa.dto.EmployeeDTO(e.employeeId, e.firstName, e.lastName,  d.departmentId, j.jobId, j.jobTitle)"
				+ " FROM Employees e  JOIN e.department d JOIN e.job j"),
		@NamedQuery(name = "Employees.isManager", query = "SELECT COUNT(m) FROM Departments d INNER JOIN d.managerId m where m.employeeId = :key"),
		@NamedQuery(name = "Employees.jobHistoryById", query = "SELECT e.jobHistoryEmployeesViaEmployeeId FROM Employees e WHERE e.employeeId = :key "),
		@NamedQuery(name = "Employees.loggin", query = "SELECT new co.com.foundation.crud.jpa.domain.User(e.employeeId, e.email, e.firstName) FROM Employees e WHERE e.email = :mail AND e.identification = :ident"),
		@NamedQuery(name = "Employees.exist", query = "SELECT COUNT(e) FROM Employees e WHERE e.employeeId =:id"),
		@NamedQuery(name = "Employees.mailExist", query = "SELECT COUNT(e.email) FROM Employees e WHERE e.email = :mail"),
		@NamedQuery(name = "Employees.belongDepartment", query = "SELECT COUNT(e.employeeId) FROM  Employees e JOIN e.department d WHERE e.employeeId = :empId AND d.departmentId = :depId"),
		@NamedQuery(name = "Employees.getJobById", query = "SELECT new co.com.foundation.crud.jpa.dto.JobDTO(j.jobId, j.jobTitle) FROM Employees e"
				+ " JOIN e.job j WHERE e.employeeId = :id"),
		@NamedQuery(name = "Employees.getDepartmentById", query = "SELECT new co.com.foundation.crud.jpa.dto.DepartmentDTO(d.departmentId, d.departmentName) FROM Employees e"
				+ " JOIN e.department d WHERE e.employeeId = :id") })
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employees implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL = "Employees.findAll";

	@Id
	@SequenceGenerator(name = "EMPLOYEES_SEQ", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEES_SEQ")
	@Column(name = "EMPLOYEE_ID", unique = true, nullable = false, updatable = false)
	@XmlAttribute
	private Long employeeId;

	@Column(name = "FIRST_NAME", length = 20, nullable = true, unique = false)
	@XmlElement
	private String firstName;

	@Column(name = "LAST_NAME", length = 25, nullable = false, unique = false)
	@XmlElement
	private String lastName;

	@Column(name = "EMAIL", length = 25, nullable = false, unique = false, updatable = true)
	@XmlElement
	private String email;

	@Column(name = "PHONE_NUMBER", length = 20, nullable = true, unique = false)
	@XmlElement
	private String phoneNumber;

	@Column(name = "HIRE_DATE", nullable = false, unique = false)
	@XmlElement
	private Date hireDate;

	@Column(name = "SALARY", nullable = true, unique = false)
	private Long salary;

	@Column(name = "COMMISSION_PCT", nullable = true, unique = false)
	@XmlElement
	private Long commissionPct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", nullable = true, unique = false, insertable = true, updatable = true)
	@XmlElement
	private Departments department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID", nullable = true, unique = true, insertable = true, updatable = true)
	@XmlElement
	private Employees manager;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID", nullable = false, unique = true, insertable = true, updatable = true)
	@XmlElement
	private Jobs job;

	@Column(name = "IDENTIFICATION", length = 14, unique = false)
	private String identification;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "MANAGER_ID")
	@XmlElement
	private List<Departments> departmentsEmployeesViaManagerId;

	@OneToMany(targetEntity = Employees.class, fetch = FetchType.LAZY, mappedBy = "manager", cascade = CascadeType.REMOVE) // ,
	@XmlElement
	private Set<Employees> employeesEmployeesViaManagerId = new HashSet<Employees>();

	@OneToMany(targetEntity = JobHistory.class, fetch = FetchType.LAZY, mappedBy = "employeeId_", cascade = CascadeType.MERGE) // ,
	@XmlElement
	private Set<JobHistory> jobHistoryEmployeesViaEmployeeId = new HashSet<JobHistory>();

	public Employees() {
		super();
	}
}
