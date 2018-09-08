package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.DepartmentRequest;
import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "DepartmentsFacade")
public class DepartmentsFacade implements ModulesFacade<DepartmentRequest, DepartmentDTO> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentsFacade.class);

	@EJB(beanName = "DepartmentPersistence")
	private Persistence<DepartmentRequest, DepartmentDTO> departmentPersistence;

	@Override
	public void create(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("star -- create method");
			departmentPersistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<DepartmentDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("star -- list-all method");
			return departmentPersistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(DepartmentRequest request)
			throws ManagerException, EmployeeNotExistException, PersistenceException {
		try {
			LOGGER.info("star -- update method");
			departmentPersistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(DepartmentRequest request) throws DepartmentAvailableException, PersistenceException {
		try {
			LOGGER.info("star -- delete method");
			departmentPersistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
