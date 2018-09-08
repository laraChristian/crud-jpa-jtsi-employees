package co.com.foundation.crud.jpa.boundary.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.entities.Jobs;
import co.com.foundation.crud.jpa.util.CommonUtils;

@Mappers(type = MapperType.JOBS)
public class JobMapperImpl implements Mapper<JobDTO, Jobs> {

	private final Logger LOGGER = LogManager.getLogger(JobMapperImpl.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Jobs map(JobDTO request) {
		LOGGER.info("start -- map method");
		Jobs job = em.find(Jobs.class, request.getJobId());

		if (CommonUtils.isNull.test(job)) {
			job = new Jobs();
			job.setJobId(request.getJobId());
		}

		job.setJobTitle(request.getJobTitle());
		job.setMinSalary(request.getMinSalary());
		job.setMaxSalary(request.getMaxSalary());
		LOGGER.info("end -- map method");
		return job;
	}

}
