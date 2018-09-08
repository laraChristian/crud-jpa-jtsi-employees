package co.com.foundation.crud.jpa.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryDTO {

	@XmlAttribute
	private String countryId;
	@XmlElement
	private String countryName;
	@XmlElement
	private Long regionId;
	@XmlElement
	private String regionName;

	public CountryDTO() {
		super();
	}

	public CountryDTO(String countryId, String countryName) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
	}

}
