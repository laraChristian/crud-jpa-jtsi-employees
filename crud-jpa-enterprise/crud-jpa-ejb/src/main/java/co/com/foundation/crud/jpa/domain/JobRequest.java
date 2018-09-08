package co.com.foundation.crud.jpa.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.JobDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "job-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobRequest {

	@XmlElement
	private boolean update;
	@XmlElement
	private JobDTO jobDTO;

	public JobRequest() {
		super();
	}
}
