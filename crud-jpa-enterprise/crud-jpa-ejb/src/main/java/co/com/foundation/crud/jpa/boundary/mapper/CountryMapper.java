package co.com.foundation.crud.jpa.boundary.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.CountryDTO;
import co.com.foundation.crud.jpa.entities.Countries;
import co.com.foundation.crud.jpa.entities.Regions;
import co.com.foundation.crud.jpa.util.CommonUtils;

@Mappers(type = MapperType.COUNTRIES)
public class CountryMapper implements Mapper<CountryDTO, Countries> {

	private final Logger LOGGER = LogManager.getLogger(CountryMapper.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Countries map(CountryDTO dto) {
		LOGGER.info("start -- map method");
		Countries country = em.find(Countries.class, dto.getCountryId());

		if (CommonUtils.isNull.test(country)) {
			country = new Countries();
			country.setCountryId(dto.getCountryId());
		}

		country.setCountryName(dto.getCountryName());
		country.setRegionId(em.find(Regions.class, dto.getRegionId()));
		LOGGER.info("end -- map method");
		return country;
	}

}
