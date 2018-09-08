package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.LocationRequest;
import co.com.foundation.crud.jpa.dto.LocationDTO;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "LocationFacade")
public class LocationFacade implements ModulesFacade<LocationRequest, LocationDTO> {

	private final Logger LOGGER = LogManager.getLogger(LocationFacade.class);

	@EJB(beanName = "LocationPersistence")
	private Persistence<LocationRequest, LocationDTO> persistence;

	@Override
	public void create(LocationRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<LocationDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(LocationRequest request) throws PersistenceException, RegionAvailableException {
		try {
			LOGGER.info("start -- delete method");
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
