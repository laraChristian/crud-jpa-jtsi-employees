package co.com.foundation.crud.jpa.persistence.impl;

import java.util.List;

import javax.ejb.EJB;
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
import co.com.foundation.crud.jpa.domain.DepartmentRequest;
import co.com.foundation.crud.jpa.domain.EmployeeRequest;
import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.entities.Departments;
import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.persistence.Persistence;
import co.com.foundation.crud.jpa.validators.AdministrativeValidator;
import co.com.foundation.crud.jpa.validators.EmployeesValidator;
import co.com.foundation.crud.jpa.validators.ValidatorType;
import co.com.foundation.crud.jpa.validators.Validators;

@Stateless(name = "DepartmentPersistence")
@LocalBean
public class DepartmentPersistence implements Persistence<DepartmentRequest, DepartmentDTO> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentPersistence.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Inject
	@Mappers(type = MapperType.DEPARTMENTS)
	private Mapper<DepartmentDTO, Departments> departmentMapper;

	@Inject
	@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
	private AdministrativeValidator departmentValidator;

	@Inject
	@Validators(type = ValidatorType.EMPLOYEE_VALIDATOR)
	private EmployeesValidator employeeValidator;

	@EJB(beanName = "EmployeesPersistence")
	Persistence<EmployeeRequest, EmployeeDTO> employeePersistence;

	@Override
	public void create(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create");
			DepartmentDTO dto = request.getDepartmentDTO();
			Departments departments = departmentMapper.map(dto);
			em.persist(departments);
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create");
		}
	}

	@Override
	public List<DepartmentDTO> listAll() throws PersistenceException {

		try {
			LOGGER.info("start -- list-all");
			return em.createNamedQuery("Departments.listAll", DepartmentDTO.class).getResultList();
		} catch (Exception e) {
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all");
		}
	}

	@Override
	public void update(DepartmentRequest request)
			throws PersistenceException, ManagerException, EmployeeNotExistException {
		try {
			LOGGER.info("start -- update-method");
			DepartmentDTO dto = request.getDepartmentDTO();

			if (dto.getManagerId() != 0) {
				if (!departmentValidator.employeeBelongDepartment(dto.getManagerId(), dto.getDepartmentId())) {
					throw new EmployeeNotExistException("[ERROR] -- Employee don't belong to this department");
				}

				if (employeeValidator.isManager(dto.getManagerId())) {
					throw new ManagerException(
							"[ERROR] -- Employee is manager from a department, first should be unassigned.");
				}
			}

			Departments department = departmentMapper.map(request.getDepartmentDTO());
			em.merge(department);
			em.flush();
		} catch (ManagerException e) {
			throw e;
		} catch (EmployeeNotExistException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("[ERROR]", e);
		} finally {
			LOGGER.info("end -- update");
		}
	}

	@Override
	public void delete(DepartmentRequest request) throws PersistenceException, DepartmentAvailableException {
		try {
			LOGGER.info("start -- delete method");
			DepartmentDTO dto = request.getDepartmentDTO();

			if (departmentValidator.departmentHaveEmployees(dto.getDepartmentId())) {
				throw new DepartmentAvailableException("[ERROR] -- this department have employees assigned");
			}

			em.remove(em.find(Departments.class, dto.getDepartmentId()));

		} catch (DepartmentAvailableException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException("[ERROR] -- " + e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	public DepartmentDTO getDepartmentByManager(final Long managerId) {
		return em.createNamedQuery("Departments.getByManager", DepartmentDTO.class).setParameter("managerId", managerId)
				.getSingleResult();
	}

}
