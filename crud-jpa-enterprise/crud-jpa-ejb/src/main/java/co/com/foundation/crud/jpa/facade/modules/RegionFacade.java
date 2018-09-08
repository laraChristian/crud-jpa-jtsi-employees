package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.RegionRequest;
import co.com.foundation.crud.jpa.dto.RegionDTO;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "RegionFacade")
public class RegionFacade implements ModulesFacade<RegionRequest, RegionDTO> {

	private final Logger LOGGER = LogManager.getLogger(RegionFacade.class);

	@EJB(beanName = "RegionPersistence")
	private Persistence<RegionRequest, RegionDTO> persistence;

	@Override
	public void create(RegionRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}

	}

	@Override
	public List<RegionDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(RegionRequest request) throws PersistenceException, DuplicateRegionException {
		try {
			LOGGER.info("start -- update method");
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(RegionRequest request) throws PersistenceException, RegionAvailableException {
		try {
			LOGGER.info("start -- delete method");
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
