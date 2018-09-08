package co.com.foundation.crud.jpa.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationRequest {

	@XmlElement
	private boolean update;
	@XmlElement
	private LocationDTO request;

	public LocationRequest() {
		super();
	}
}
