package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.JobRequest;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "JobFacade")
public class JobFacade implements ModulesFacade<JobRequest, JobDTO> {

	private final Logger LOGGER = LogManager.getLogger(JobFacade.class);

	@EJB(beanName = "JobPersistence")
	private Persistence<JobRequest, JobDTO> jobPersistence;

	@Override
	public void create(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			jobPersistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<JobDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list method");
			return jobPersistence.listAll();
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@Override
	public void update(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			jobPersistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method");
			jobPersistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
