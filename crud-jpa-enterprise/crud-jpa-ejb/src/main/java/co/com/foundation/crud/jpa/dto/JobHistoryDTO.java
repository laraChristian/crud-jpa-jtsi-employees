package co.com.foundation.crud.jpa.dto;

import java.util.Date;

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
@AllArgsConstructor
@Builder
@XmlRootElement(name = "job-history-dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobHistoryDTO {

	@XmlAttribute
	private Long id;
	@XmlElement
	private Date startDate;
	@XmlElement
	private Date endDate;
	@XmlElement
	private Long departmentId;
	@XmlElement
	private String departmentName;
	@XmlElement
	private Long employeeId;
	@XmlElement
	private String firstName;
	@XmlElement
	private String jobId;
	@XmlElement
	private String jobTitle;

	public JobHistoryDTO() {
		super();
	}

	public JobHistoryDTO(Long id, Date startDate, Date endDate, Long employeeId) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeId = employeeId;
	}

}
