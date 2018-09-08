package co.com.foundation.crud.jpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.JobHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@XmlRootElement(name = "job-history-response")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobHistoryResponse {

	@XmlElement
	private String message;
	@XmlElement
	private String cause;
	@XmlElement
	private List<JobHistoryDTO> jobHistories;

	public JobHistoryResponse() {
		super();
	}

}
