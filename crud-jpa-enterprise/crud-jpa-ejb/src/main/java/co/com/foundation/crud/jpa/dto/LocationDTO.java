package co.com.foundation.crud.jpa.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDTO {

	@XmlAttribute
	private Long locationId;
	@XmlElement
	private String streetAddress;
	@XmlElement
	private String postalCode;
	@XmlElement
	private String city;
	@XmlElement
	private String stateProvince;
	@XmlAttribute
	private String countryId;
	@XmlElement
	private String countryName;
	@XmlElement
	private Long longitude;
	@XmlElement
	private Long latitude;

	public LocationDTO() {
		super();
	}

	public LocationDTO(Long locationId, String city) {
		super();
		this.locationId = locationId;
		this.city = city;
	}

	public LocationDTO(Long locationId, String streetAddress, String postalCode, String city, String stateProvince,
			String countryId, String countryName) {
		super();
		this.locationId = locationId;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.stateProvince = stateProvince;
		this.countryId = countryId;
		this.countryName = countryName;
	}

}
