package co.com.foundation.crud.jpa.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.RegionDTO;
import lombok.Getter;

@Getter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RegionRequest {

	@XmlElement
	private boolean update;
	@XmlElement
	private RegionDTO request;

	public RegionRequest() {
		super();
	}
}
