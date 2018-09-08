package co.com.foundation.crud.jpa.persistence.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.Mapper;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.domain.RegionRequest;
import co.com.foundation.crud.jpa.dto.RegionDTO;
import co.com.foundation.crud.jpa.entities.Regions;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "RegionPersistence")
@LocalBean
public class RegionPersistence implements Persistence<RegionRequest, RegionDTO> {

	private final Logger LOGGER = LogManager.getLogger(RegionPersistence.class);

	public RegionPersistence() {
		super();
	}

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Mappers(type = MapperType.REGIONS)
	private Mapper<RegionDTO, Regions> mapper;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator validator;

	@Override
	public void create(RegionRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- create method");

			if (validator.existRegion(request.getRequest().getRegionName())) {
				throw new DuplicateRegionException(
						"The region " + request.getRequest().getRegionName() + " already exist");
			}

			em.persist(mapper.map(request.getRequest()));
		} catch (DuplicateRegionException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<RegionDTO> listAll() throws PersistenceException {
		try {
			return em.createNamedQuery("Regions.listCMB", RegionDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	@Override
	public void update(RegionRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- create method");
			RegionDTO dto = request.getRequest();

			if (validator.regionNameIsDuplicate(dto.getRegionName(), dto.getRegionId())) {
				throw new DuplicateRegionException("This region " + dto.getRegionName() + " already exist");
			}

			em.merge(mapper.map(dto));
		} catch (DuplicateRegionException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public void delete(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");

			if (validator.regionHaveCountries(request.getRequest().getRegionId())) {
				throw new RegionAvailableException(
						"This region is present in different countries, should be unassigned for this countries");
			}

			em.remove(em.find(Regions.class, request.getRequest().getRegionId()));
		} catch (RegionAvailableException e) {
			throw new RegionAvailableException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	public List<RegionDTO> listToCmb() throws PersistenceException {
		try {
			return em.createNamedQuery("Regions.listCMB", RegionDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

}
