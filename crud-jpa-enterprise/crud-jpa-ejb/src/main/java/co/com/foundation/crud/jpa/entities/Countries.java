package co.com.foundation.crud.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Countries")
@NamedQueries({
		@NamedQuery(name = "Countries.findAll", query = "SELECT new co.com.foundation.crud.jpa.dto.CountryDTO(c.countryId, c.countryName, r.regionId, r.regionName)"
				+ " FROM Countries c JOIN c.regionId r"),
		@NamedQuery(name = "Countries.toCMB", query = "SELECT new co.com.foundation.crud.jpa.dto.CountryDTO(c.countryId, c.countryName) FROM Countries c"),
		@NamedQuery(name = "Countries.exist", query = "SELECT COUNT(c) FROM Countries c WHERE c.countryId = :id"),
		@NamedQuery(name = "Countries.haveLocations", query = "SELECT COUNT(l) FROM Locations l JOIN l.countryId c WHERE c.countryId = :id") })
@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class Countries implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COUNTRY_ID", length = 2)
	@XmlAttribute
	private String countryId;

	@Column(name = "COUNTRY_NAME", length = 40, nullable = true, unique = false)
	@XmlElement
	private String countryName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID", nullable = true, unique = false, insertable = true, updatable = true)
	@XmlElement
	private Regions regionId;

	@OneToMany(targetEntity = co.com.foundation.crud.jpa.entities.Locations.class, fetch = FetchType.LAZY, mappedBy = "countryId", cascade = CascadeType.REMOVE) // ,
	@XmlElement
	private Set<Locations> locationsCountriesViaCountryId = new HashSet<Locations>();

	public Countries() {
		super();
	}

}
