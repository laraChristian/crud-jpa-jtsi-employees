package co.com.foundation.crud.jpa.persistence.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.JobHistoryRequest;
import co.com.foundation.crud.jpa.dto.JobHistoryDTO;
import co.com.foundation.crud.jpa.entities.JobHistory;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "JobHistoryPersistence")
public class JobHistoryPersistence implements Persistence<JobHistoryRequest, JobHistoryDTO> {

	private final Logger LOGGER = LogManager.getLogger(JobHistoryPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public void create(JobHistoryRequest t) throws PersistenceException {
	}

	@Override
	public List<JobHistoryDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			List<JobHistoryDTO> jobHistories = em.createNamedQuery("JobHistory.FIND_ALL", JobHistoryDTO.class)
					.getResultList();
			return jobHistories;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	@Override
	public void update(JobHistoryRequest t) throws PersistenceException {
	}

	@Override
	public void delete(JobHistoryRequest request) throws PersistenceException {
		LOGGER.info("start -- delete method with employeeId: {}", request.getRequest().getId());
		List<JobHistory> jobHistories = em.createNamedQuery("Employees.jobHistoryById", JobHistory.class)
				.setParameter("key", request.getRequest().getId()).getResultList();

		if (!CollectionUtils.isEmpty(jobHistories)) {
			jobHistories.forEach(jobHistory -> {
				LOGGER.info("removing job-history with start-date: {}", jobHistory.getStartDate());
				em.remove(jobHistory);
			});
			em.flush();
		} else {
			LOGGER.info("employee don't have job-history");
		}
		LOGGER.info("end -- delete method with employeeId: {}", request.getRequest().getEmployeeId());
	}

}
