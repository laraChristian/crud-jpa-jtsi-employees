package co.com.foundation.crud.jpa.boundary.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.RegionDTO;
import co.com.foundation.crud.jpa.entities.Regions;
import co.com.foundation.crud.jpa.util.CommonUtils;

@Mappers(type = MapperType.REGIONS)
public class RegionMapper implements Mapper<RegionDTO, Regions> {

	private final Logger LOGGER = LogManager.getLogger(RegionMapper.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Regions map(RegionDTO request) {
		LOGGER.info("start -- map method");
		Regions regions = em.find(Regions.class, request.getRegionId());

		if (CommonUtils.isNull.test(regions)) {
			regions = new Regions();
		}

		regions.setRegionName(request.getRegionName());

		LOGGER.info("end -- map method");
		return regions;
	}

}
