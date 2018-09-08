package co.com.foundation.crud.jpa.persistence.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.Mapper;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.domain.EmployeeRequest;
import co.com.foundation.crud.jpa.domain.JobHistoryRequest;
import co.com.foundation.crud.jpa.domain.LoginRequest;
import co.com.foundation.crud.jpa.domain.User;
import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.dto.JobHistoryDTO;
import co.com.foundation.crud.jpa.entities.Employees;
import co.com.foundation.crud.jpa.entities.JobHistory;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.InvalidCredentialsException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.EmployeesValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "EmployeesPersistence")
@LocalBean
public class EmployeesPersistence implements Persistence<EmployeeRequest, EmployeeDTO> {

	private final Logger LOGGER = LogManager.getLogger(EmployeesPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Mappers(type = MapperType.EMPLOYEES)
	private Mapper<EmployeeDTO, Employees> employeeMapper;

	@Inject
	@Validators(type = ValidatorType.EMPLOYEE_VALIDATOR)
	private EmployeesValidator validator;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator departmentValidator;

	@EJB(beanName = "JobHistoryPersistence")
	private Persistence<JobHistoryRequest, JobHistoryDTO> jobHistoryPersistence;

	@Override
	public List<EmployeeDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return em.createNamedQuery("Employees.findAll", EmployeeDTO.class).getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		}
	}

	public List<EmployeeDTO> listToCMB() throws PersistenceException {
		try {
			LOGGER.info("start -- list-to-cmb method");
			return em.createNamedQuery("Employees.listCMB", EmployeeDTO.class).getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		}
	}

	@Override
	public void create(EmployeeRequest request) throws DuplicateMailException, PersistenceException {
		try {
			LOGGER.info("start -- create method");
			Employees employee = employeeMapper.map(request.getEmployee());
			if (validator.mailExist(employee.getEmail())) {
				throw new DuplicateMailException("[ERROR] -- Duplicate email");
			}
			em.persist(employee);
			em.flush();
		} catch (DuplicateMailException e) {
			throw new DuplicateMailException(e.getMessage(), e);
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public void update(EmployeeRequest request) throws PersistenceException, ManagerException {
		try {
			LOGGER.info("start -- update method");
			EmployeeDTO employeeDTO = request.getEmployee();

			JobDTO jDTO = em.createNamedQuery("Employees.getJobById", JobDTO.class)
					.setParameter("id", employeeDTO.getId()).getSingleResult();
			DepartmentDTO dDTO = em.createNamedQuery("Employees.getDepartmentById", DepartmentDTO.class)
					.setParameter("id", employeeDTO.getId()).getSingleResult();

			if (dDTO.getDepartmentId() != employeeDTO.getDepartmentId()) {
				if (validator.isManager(employeeDTO.getId())) {
					throw new ManagerException(
							"[ERROR] -- actually this employee is manager, should be unassigned from actual department");
				}
			}

			if (!jDTO.getJobId().equals(employeeDTO.getJobId())
					|| !dDTO.getDepartmentId().equals(employeeDTO.getDepartmentId())) {
				if (validator.findJobHistories(employeeDTO.getId())) {
					JobHistory jobHistory = em.find(JobHistory.class,
							em.createNamedQuery("JobHistory.maxByEmployeeId", Long.class)
									.setParameter("key", employeeDTO.getId()).getSingleResult());
					jobHistory.setEndDate(new Date());
					em.merge(jobHistory);
				}
			}

			Employees employee = employeeMapper.map(request.getEmployee());
			em.merge(employee);
		} catch (ManagerException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(EmployeeRequest request)
			throws PersistenceException, ManagerException, EmployeeNotExistException {
		try {
			LOGGER.info("start -- delete method");
			EmployeeDTO employeeDTO = request.getEmployee();

			if (!validator.exist(employeeDTO.getId())) {
				throw new EmployeeNotExistException("[ERROR] -- employee doesn't exist");
			}

			if (validator.isManager(employeeDTO.getId())) {
				throw new ManagerException(
						"[ERROR] -- the employee is manager, should be unassigned of the department");
			}

			if (validator.findJobHistories(employeeDTO.getId())) {
				jobHistoryPersistence.delete(JobHistoryRequest.builder()
						.request(JobHistoryDTO.builder().id(employeeDTO.getId()).build()).build());
			}

			Employees employee = em.find(Employees.class, request.getEmployee().getId());
			em.remove(employee);
			em.flush();
		} catch (EmployeeNotExistException e) {
			throw e;
		} catch (ManagerException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- in employees api", e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	public User loggin(final LoginRequest request) throws PersistenceException, InvalidCredentialsException {
		try {
			LOGGER.info("start -- loggin method");
			return em.createNamedQuery("Employees.loggin", User.class)
					.setParameter("mail", request.getUser().getEmail())
					.setParameter("ident", request.getUser().getPassword()).getSingleResult();
		} catch (NoResultException e) {
			throw new InvalidCredentialsException("[ERROR] -- not valid cridentials");
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- loggin method");
		}
	}

}
