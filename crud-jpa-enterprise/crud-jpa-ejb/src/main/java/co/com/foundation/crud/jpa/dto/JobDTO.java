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
@AllArgsConstructor
@Builder
@XmlRootElement(name = "job-dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDTO {

	@XmlAttribute
	private String jobId;
	@XmlElement
	private String jobTitle;
	@XmlElement
	private Long minSalary;
	@XmlElement
	private Long maxSalary;

	public JobDTO() {
		super();
	}

	public JobDTO(String jobId, String jobTitle) {
		super();
		this.jobId = jobId;
		this.jobTitle = jobTitle;
	}

}
