package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "JobHistory")
@Table(name = "JOB_HISTORY")
@NamedQueries({ @NamedQuery(name = "JobHistory.listAll", query = "SELECT j FROM JobHistory j"),
		@NamedQuery(name = "JobHistory.employeeHaveHistories", query = "SELECT COUNT(j) FROM JobHistory j WHERE j.employeeId = :key"),
		@NamedQuery(name = "JobHistory.maxByEmployeeId", query = "SELECT MAX(jh.id) FROM JobHistory jh WHERE jh.employeeId = :key"),
		@NamedQuery(name = "JobHistory.byId", query = "SELECT new co.com.foundation.crud.jpa.dto.JobHistoryDTO(jh.id, jh.startDate, jh.endDate, jh.employeeId) FROM JobHistory jh WHERE jh.id = :key") })
@XmlRootElement(name = "jobHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "JOBHISTORY_SEQ", sequenceName = "JOBHISTORY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBHISTORY_SEQ")
	@Column(name = "JOB_HISTORY_ID", unique = true, nullable = false)
	@XmlAttribute
	private Long id;

	@Column(name = "START_DATE", nullable = false, unique = false, insertable = false, updatable = false)
	@XmlElement
	private Date startDate;

	@Column(name = "END_DATE", nullable = true, unique = false)
	@XmlElement
	private Date endDate;

	@Column(name = "EMPLOYEE_ID", nullable = false, unique = false)
	@XmlElement
	private Long employeeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", nullable = true, unique = false, insertable = false, updatable = false)
	@XmlElement
	private Departments departmentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false, unique = true, insertable = false, updatable = false)
	@XmlElement
	private Employees employeeId_;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID", nullable = false, unique = true, insertable = false, updatable = false)
	@XmlElement
	private Jobs jobId;

	public JobHistory() {
		super();
	}

}
