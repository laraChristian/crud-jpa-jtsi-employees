package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Jobs")
@NamedQueries({
		@NamedQuery(name = "Jobs.listAll", query = "SELECT new co.com.foundation.crud.jpa.dto.JobDTO(j.jobId, j.jobTitle, j.minSalary, j.maxSalary) FROM Jobs j"),
		@NamedQuery(name = "Jobs.listCMB", query = "SELECT new co.com.foundation.crud.jpa.dto.JobDTO(j.jobId, j.jobTitle) FROM Jobs j"),
		@NamedQuery(name = "Jobs.exist", query = "SELECT COUNT(j) FROM Jobs j WHERE j.jobId = :id"),
		@NamedQuery(name = "Jobs.haveEmployees", query = "SELECT COUNT(e) FROM Employees e JOIN e.job j WHERE j.jobId = :id") })
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class Jobs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JOB_ID", length = 10, updatable = true)
	@XmlAttribute
	private String jobId;

	@Column(name = "JOB_TITLE", length = 35, nullable = false, unique = false)
	@XmlElement
	private String jobTitle;

	@Column(name = "MIN_SALARY", nullable = true, unique = false)
	@XmlElement
	private Long minSalary;

	@Column(name = "MAX_SALARY", nullable = true, unique = false)
	@XmlElement
	private Long maxSalary;

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.Employees.class, fetch = FetchType.LAZY, mappedBy = "job", cascade = CascadeType.MERGE)
	@XmlElement
	private Set<Employees> employeesJobsViaJobId = new HashSet<Employees>();

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.JobHistory.class, fetch = FetchType.LAZY, mappedBy = "jobId", cascade = CascadeType.MERGE)
	@XmlElement
	private Set<JobHistory> jobHistoryJobsViaJobId = new HashSet<JobHistory>();

	public Jobs() {
		super();
	}

}
