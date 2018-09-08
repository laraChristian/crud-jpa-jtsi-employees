package co.com.foundation.crud.jpa.boundary.mapper;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.entities.Departments;
import co.com.foundation.crud.jpa.entities.Employees;
import co.com.foundation.crud.jpa.entities.Jobs;
import co.com.foundation.crud.jpa.util.CommonUtils;

@Mappers(type = MapperType.EMPLOYEES)
public class EmployeeMapperImpl implements Mapper<EmployeeDTO, Employees> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeMapperImpl.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Employees map(EmployeeDTO employeeDTO) {
		LOGGER.info("start -- map method");

		if (!em.isJoinedToTransaction()) {
			em.joinTransaction();
		}

		Employees persist = em.find(Employees.class, employeeDTO.getId());
		if (CommonUtils.isNull.test(persist)) {
			persist = new Employees();
			persist.setHireDate(new Date());
		} else {
			persist.setHireDate(employeeDTO.getHireDate());
		}

		persist.setManager(em.find(Employees.class, employeeDTO.getManagerId()));
		persist.setDepartment(em.find(Departments.class, employeeDTO.getDepartmentId()));
		persist.setJob(em.find(Jobs.class, employeeDTO.getJobId()));
		persist.setFirstName(employeeDTO.getFirstName());
		persist.setLastName(employeeDTO.getLastName());
		persist.setEmail(employeeDTO.getEmail());
		persist.setPhoneNumber(employeeDTO.getPhoneNumber());
		persist.setSalary(employeeDTO.getSalary());
		persist.setCommissionPct(employeeDTO.getCommissionPct());
		persist.setIdentification(employeeDTO.getIdentification());
		LOGGER.info("end -- map method");
		return persist;
	}

}
