package co.com.foundation.crud.jpa.persistence;

import java.util.List;

import javax.ejb.Local;

import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateJobException;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.JobAvailableException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;

@Local
public interface Persistence<I, O> {

	void create(I request) throws PersistenceException, DuplicateMailException, EmployeeNotExistException,
			ManagerException, DuplicateJobException, DuplicateRegionException;

	List<O> listAll() throws PersistenceException;

	void update(I request)
			throws DepartmentAvailableException, PersistenceException, ManagerException, DuplicateRegionException;

	void delete(I request) throws PersistenceException, ManagerException, EmployeeNotExistException,
			DepartmentAvailableException, JobAvailableException, RegionAvailableException;

}
