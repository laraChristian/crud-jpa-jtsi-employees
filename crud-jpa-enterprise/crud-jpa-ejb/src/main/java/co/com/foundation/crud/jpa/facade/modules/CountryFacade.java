package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.CountryRequest;
import co.com.foundation.crud.jpa.dto.CountryDTO;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "CountryFacade")
public class CountryFacade implements ModulesFacade<CountryRequest, CountryDTO> {

	private final Logger LOGGER = LogManager.getLogger(CountryFacade.class);

	@EJB(beanName = "CountryPersistence")
	private Persistence<CountryRequest, CountryDTO> countryPersistence;

	@Override
	public void create(CountryRequest request) throws DuplicateMailException, PersistenceException {
		try {
			LOGGER.info("start -- create method");
			countryPersistence.create(request);
		} finally {
			LOGGER.info("end -- crate method");
		}
	}

	@Override
	public List<CountryDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return countryPersistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(CountryRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			countryPersistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(CountryRequest request) throws PersistenceException, RegionAvailableException {
		try {
			LOGGER.info("start -- delete method");
			countryPersistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
