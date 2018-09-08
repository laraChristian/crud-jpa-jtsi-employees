package co.com.foundation.crud.jpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.com.foundation.crud.jpa.dto.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryResponse {

	@XmlElement
	private String message;
	@XmlElement
	private boolean success;
	@XmlElement
	private List<CountryDTO> countriesDTO;

	public CountryResponse() {
		super();
	}
}
