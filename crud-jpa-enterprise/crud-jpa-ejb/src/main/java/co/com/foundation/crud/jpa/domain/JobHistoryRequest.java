package co.com.foundation.crud.jpa.domain;

import co.com.foundation.crud.jpa.dto.JobHistoryDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JobHistoryRequest {

	private boolean update;
	private JobHistoryDTO request;

}
