package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Locations")
@NamedQueries({
		@NamedQuery(name = "Locations.listCMB", query = "SELECT new  co.com.foundation.crud.jpa.dto.LocationDTO(l.locationId, l.city) FROM Locations l"),
		@NamedQuery(name = "Locations.exist", query = "SELECT COUNT(l) FROM Locations l WHERE l.locationId = :id"),
		@NamedQuery(name = "Locations.listAll", query = "SELECT new  co.com.foundation.crud.jpa.dto.LocationDTO(l.locationId, l.streetAddress, l.postalCode,"
				+ " l.city, l.stateProvince, c.countryId, c.countryName) FROM Locations l JOIN l.countryId c"),
		@NamedQuery(name = "Locations.haveDepartments", query = "SELECT COUNT(d) FROM Departments d JOIN d.locationId l WHERE l.locationId = :id") })
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Locations implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOCATIONS_SEQ", sequenceName = "LOCATIONS_SEQ", allocationSize = 1)
	@Column(name = "LOCATION_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATIONS_SEQ")
	@XmlAttribute
	private Long locationId;

	@Column(name = "STREET_ADDRESS", length = 40, nullable = true, unique = false)
	@XmlElement
	private String streetAddress;

	@Column(name = "POSTAL_CODE", length = 12, nullable = true, unique = false)
	@XmlElement
	private String postalCode;

	@Column(name = "CITY", length = 30, nullable = false, unique = true)
	@XmlElement
	private String city;

	@Column(name = "STATE_PROVINCE", length = 25, nullable = true, unique = true)
	@XmlElement
	private String stateProvince;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = true, unique = true, insertable = true, updatable = true)
	@XmlElement
	private Countries countryId;

	@Column(name = "LONGITUDE", length = 20, nullable = true)
	@XmlElement
	private Long longitude;

	@Column(name = "LATITUDE", length = 20, nullable = true)
	@XmlElement
	private Long latitude;

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.Departments.class, fetch = FetchType.LAZY, mappedBy = "locationId", cascade = CascadeType.REMOVE) // ,
	@XmlElement
	private Set<Departments> departmentsLocationsViaLocationId = new HashSet<Departments>();

	public Locations() {
		super();
	}
}
