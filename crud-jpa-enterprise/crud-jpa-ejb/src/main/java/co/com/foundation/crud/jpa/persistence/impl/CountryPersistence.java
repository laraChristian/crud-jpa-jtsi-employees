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
import co.com.foundation.crud.jpa.domain.CountryRequest;
import co.com.foundation.crud.jpa.dto.CountryDTO;
import co.com.foundation.crud.jpa.entities.Countries;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "CountryPersistence")
@LocalBean
public class CountryPersistence implements Persistence<CountryRequest, CountryDTO> {

	private final Logger LOGGER = LogManager.getLogger(CountryPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator validator;

	@Inject
	@Mappers(type = MapperType.COUNTRIES)
	private Mapper<CountryDTO, Countries> mapper;

	@Override
	public void create(CountryRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- create method");
			if (validator.exitCountry(request.getCountryDTO().getCountryId())) {
				throw new DuplicateRegionException("This country already exist");
			}

			em.persist(mapper.map(request.getCountryDTO()));
		} catch (DuplicateRegionException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<CountryDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return em.createNamedQuery("Countries.findAll", CountryDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(CountryRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			em.merge(mapper.map(request.getCountryDTO()));
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(CountryRequest request) throws PersistenceException, RegionAvailableException {
		try {
			LOGGER.info("start -- delete method");

			if (validator.countryHaveLocations(request.getCountryDTO().getCountryId())) {
				throw new RegionAvailableException("This country is assigned in multiple locations");
			}

			em.remove(em.find(Countries.class, request.getCountryDTO().getCountryId()));
		} catch (RegionAvailableException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	public List<CountryDTO> listToCMB() throws PersistenceException {
		try {
			LOGGER.info("start -- list-to-cmb method");
			return em.createNamedQuery("Countries.toCMB", CountryDTO.class).getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-to-cmb method");
		}
	}
}
