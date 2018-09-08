package co.com.foundation.crud.jpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@XmlRootElement(name = "job-response")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobResponse {

	@XmlElement
	private boolean success;
	@XmlElement
	private List<JobDTO> jobsDTO;
	@XmlElement
	private String message;

	public JobResponse() {
		super();
	}

}
