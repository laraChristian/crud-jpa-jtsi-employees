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
@Entity(name = "Regions")
@NamedQueries({ @NamedQuery(name = "Regions.findAll", query = "SELECT a FROM Regions a"),
		@NamedQuery(name = "Regions.listCMB", query = "SELECT NEW co.com.foundation.crud.jpa.dto.RegionDTO(r.regionId, r.regionName) FROM Regions r"),
		@NamedQuery(name = "Regions.existByName", query = "SELECT COUNT(r) FROM Regions r WHERE r.regionName = :name"),
		@NamedQuery(name = "Regions.nameIsDuplicate", query = "SELECT COUNT(r) FROM Regions r WHERE  r.regionName = :name AND r.regionId != :id"),
		@NamedQuery(name = "Regions.haveCountries", query = "SELECT COUNT(c) FROM Countries c JOIN c.regionId r WHERE r.regionId = :id") })
@XmlRootElement(name = "region")
@XmlAccessorType(XmlAccessType.FIELD)
public class Regions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "REGIONS_SEQ", sequenceName = "REGIONS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGIONS_SEQ")
	@Column(name = "REGION_ID")
	@XmlAttribute
	private Long regionId;

	@Column(name = "REGION_NAME", length = 25, nullable = true, unique = false)
	@XmlElement
	private String regionName;

	@OneToMany(targetEntity = Countries.class, fetch = FetchType.LAZY, mappedBy = "regionId", cascade = CascadeType.REMOVE)
	@XmlElement
	private Set<Countries> countriesRegionsViaRegionId = new HashSet<Countries>();

	public Regions() {
		super();
	}
}
