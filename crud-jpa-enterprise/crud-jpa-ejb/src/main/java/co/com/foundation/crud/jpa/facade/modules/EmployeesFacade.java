package co.com.foundation.crud.jpa.facade.modules;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.EmployeeRequest;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.persistence.Persistence;

@Stateless(name = "EmployeesFacade")
public class EmployeesFacade implements ModulesFacade<EmployeeRequest, EmployeeDTO> {

	private final Logger LOGGER = LogManager.getLogger(EmployeesFacade.class);

	@EJB(beanName = "EmployeesPersistence")
	private Persistence<EmployeeRequest, EmployeeDTO> employeePersistence;

	@Override
	public void create(EmployeeRequest request) throws DuplicateMailException, PersistenceException {
		try {
			LOGGER.info("facade -- start -- create method");
			employeePersistence.create(request);
		} finally {
			LOGGER.info("facade -- end -- create method");
		}
	}

	@Override
	public List<EmployeeDTO> listAll() throws PersistenceException {
		try {
			LOGGER.info("facade -- start -- list-all method");
			return employeePersistence.listAll();
		} finally {
			LOGGER.info("facade -- end -- list-all method");
		}
	}

	@Override
	public void update(EmployeeRequest request)
			throws DepartmentAvailableException, DuplicateMailException, PersistenceException {
		try {
			LOGGER.info("facade -- start -- update method");
			employeePersistence.update(request);
		} finally {
			LOGGER.info("facade -- end -- update method");
		}
	}

	@Override
	public void delete(EmployeeRequest request)
			throws ManagerException, EmployeeNotExistException, PersistenceException {
		try {
			LOGGER.info("facade -- start -- delete method");
			employeePersistence.delete(request);
		} finally {
			LOGGER.info("facade -- end -- delete method");
		}
	}

}
