package co.com.foundation.crud.jpa.boundary.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.LocationDTO;
import co.com.foundation.crud.jpa.entities.Countries;
import co.com.foundation.crud.jpa.entities.Locations;
import co.com.foundation.crud.jpa.persistence.impl.LocationPersistence;

@Mappers(type = MapperType.LOCATIONS)
public class LocationMapperImpl implements Mapper<LocationDTO, Locations> {

	private final Logger LOGGER = LogManager.getLogger(LocationPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Locations map(LocationDTO dto) {
		LOGGER.info("start -- map method");
		Locations location = em.find(Locations.class, dto.getLocationId());

		if (location == null) {
			location = new Locations();
		}

		location.setCountryId(em.find(Countries.class, dto.getCountryId()));
		location.setCity(dto.getCity());
		location.setPostalCode(dto.getPostalCode());
		location.setStateProvince(dto.getStateProvince());
		location.setStreetAddress(dto.getStreetAddress());
		LOGGER.info("end -- map method");
		return location;
	}

}
