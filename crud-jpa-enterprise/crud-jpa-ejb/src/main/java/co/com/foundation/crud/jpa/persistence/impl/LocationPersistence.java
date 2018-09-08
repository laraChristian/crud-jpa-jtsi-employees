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
import co.com.foundation.crud.jpa.domain.LocationRequest;
import co.com.foundation.crud.jpa.dto.LocationDTO;
import co.com.foundation.crud.jpa.entities.Locations;
import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "LocationPersistence")
@LocalBean
public class LocationPersistence implements Persistence<LocationRequest, LocationDTO> {

	private final Logger LOGGER = LogManager.getLogger(LocationPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Mappers(type = MapperType.LOCATIONS)
	private Mapper<LocationDTO, Locations> mapper;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator validator;

	@Override
	public void create(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			Locations location = mapper.map(request.getRequest());
			em.persist(location);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<LocationDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return em.createNamedQuery("Locations.listAll", LocationDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(LocationRequest request) throws DepartmentAvailableException, PersistenceException {
		try {
			LOGGER.info("start -- update method");
			Locations location = mapper.map(request.getRequest());
			em.merge(location);
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(LocationRequest request) throws PersistenceException, RegionAvailableException {
		try {
			LOGGER.info("start -- delete method");

			if (validator.locationHaveDepartments(request.getRequest().getLocationId())) {
				throw new RegionAvailableException("This location is assigned to different departments");
			}

			em.remove(em.find(Locations.class, request.getRequest().getLocationId()));
		} catch (RegionAvailableException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	public List<LocationDTO> listToCMB() throws PersistenceException {
		try {
			return em.createNamedQuery("Locations.listCMB", LocationDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		}
	}
}
