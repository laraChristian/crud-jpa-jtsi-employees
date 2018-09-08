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
import co.com.foundation.crud.jpa.domain.JobRequest;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.entities.Jobs;
import co.com.foundation.crud.jpa.exceptions.DuplicateJobException;
import co.com.foundation.crud.jpa.exceptions.JobAvailableException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "JobPersistence")
@LocalBean
public class JobPersistence implements Persistence<JobRequest, JobDTO> {

	private final Logger LOGGER = LogManager.getLogger(JobPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator validator;

	@Inject
	@Mappers(type = MapperType.JOBS)
	private Mapper<JobDTO, Jobs> jobMapper;

	@Override
	public void create(JobRequest request) throws DuplicateJobException, PersistenceException {
		try {
			LOGGER.info("start -- create method");
			JobDTO dto = request.getJobDTO();

			if (validator.existJob(dto.getJobId())) {
				throw new DuplicateJobException("[ERROR] -- this job id already exist");
			}

			Jobs job = jobMapper.map(dto);
			em.persist(job);
		} catch (DuplicateJobException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		} finally {
			LOGGER.info("start -- create method");
		}
	}

	@Override
	public List<JobDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return em.createNamedQuery("Jobs.listAll", JobDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- job persistence " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			Jobs job = jobMapper.map(request.getJobDTO());
			em.merge(job);
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(JobRequest request) throws PersistenceException, ManagerException, JobAvailableException {
		try {
			LOGGER.info("start -- delete method");
			if (validator.jobHaveEmployees(request.getJobDTO().getJobId())) {
				throw new JobAvailableException("[ERROR] -- this job have employees assigned");
			}

			em.remove(jobMapper.map(request.getJobDTO()));

		} catch (JobAvailableException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	public List<JobDTO> listToCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-to-cmb method");
			return em.createNamedQuery("Jobs.listCMB", JobDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- job persistence " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-to-cmb method");
		}
	}

}
