package co.com.foundation.crud.jpa.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.LoginRequest;
import co.com.foundation.crud.jpa.domain.User;
import co.com.foundation.crud.jpa.dto.CountryDTO;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.dto.LocationDTO;
import co.com.foundation.crud.jpa.dto.RegionDTO;
import co.com.foundation.crud.jpa.exceptions.InvalidCredentialsException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.persistence.impl.CountryPersistence;
import co.com.foundation.crud.jpa.persistence.impl.EmployeesPersistence;
import co.com.foundation.crud.jpa.persistence.impl.JobPersistence;
import co.com.foundation.crud.jpa.persistence.impl.LocationPersistence;
import co.com.foundation.crud.jpa.persistence.impl.RegionPersistence;

@Stateless(name = "SystemFacade")
@LocalBean
public class SystemFacade {

	private final Logger LOGGER = LogManager.getLogger(SystemFacade.class);

	@EJB
	private EmployeesPersistence employeesBoundary;

	@EJB
	private LocationPersistence locationPersistence;

	@EJB
	private JobPersistence jobPersistence;

	@EJB
	private CountryPersistence countryPersistence;

	@EJB
	private RegionPersistence regionPersistence;

	public User loggin(final LoginRequest request) throws InvalidCredentialsException, PersistenceException {
		try {
			LOGGER.info("start -- loggin method");
			return employeesBoundary.loggin(request);
		} finally {
			LOGGER.info("start -- loggin method");
		}
	}

	public List<EmployeeDTO> listManagersCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-managers method");
			return employeesBoundary.listToCMB();
		} finally {
			LOGGER.info("end -- list-managers method");
		}
	}

	public List<LocationDTO> listLocationsCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-locations method");
			return locationPersistence.listToCMB();
		} finally {
			LOGGER.info("end -- list-locations method");
		}
	}

	public List<CountryDTO> listCountriesCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-locations method");
			return countryPersistence.listToCMB();
		} finally {
			LOGGER.info("end -- list-locations method");
		}
	}

	public List<JobDTO> listJobsCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-jobs method");
			return jobPersistence.listToCmb();
		} finally {
			LOGGER.info("start -- list-jobs method");
		}
	}

	public List<RegionDTO> listRegionsCmb() throws PersistenceException {
		try {
			LOGGER.info("start list-to-cmb method");
			return regionPersistence.listToCmb();
		} finally {
			LOGGER.info("end list-to-cmb method");
		}
	}
}
