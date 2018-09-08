package co.com.foundation.crud.jpa.facade;

import java.util.List;

import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;

public interface ModulesFacade<I, O> {

	void create(final I request) throws DuplicateMailException, PersistenceException, DuplicateRegionException;

	List<O> listAll() throws PersistenceException;

	void update(final I request)
			throws PersistenceException, DepartmentAvailableException, DuplicateMailException, DuplicateRegionException;

	void delete(final I request)
			throws PersistenceException, ManagerException, EmployeeNotExistException, RegionAvailableException;
}
