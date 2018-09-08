package co.com.foundation.crud.jpa.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Validators(type = ValidatorType.ADMINISTRATIVE_VALIDATOR)
public class AdministrativeValidator {

	private final Logger LOGGER = LogManager.getLogger(AdministrativeValidator.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	public boolean existJob(final String jobId) {
		return em.createNamedQuery("Jobs.exist", Long.class).setParameter("id", jobId).getSingleResult() > 0;
	}

	public boolean existDepartment(final Long departmentId) throws PersistenceException {
		try {
			LOGGER.info("start -- exist");
			return em.createNamedQuery("Departments.exist", Long.class).setParameter("id", departmentId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("start -- exist");
		}
	}

	public boolean existLocation(final Long locationId) throws PersistenceException {
		try {
			LOGGER.info("start -- exit-location method");
			return em.createNamedQuery("Locations.exist", Long.class).setParameter("id", locationId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- exit-location method");
		}
	}

	public boolean exitCountry(final String countryId) throws PersistenceException {
		try {
			return em.createNamedQuery("Countries.exist", Long.class).setParameter("id", countryId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	public boolean existRegion(final String regionName) throws PersistenceException {
		try {
			LOGGER.info("start -- exit-region method");
			return em.createNamedQuery("Regions.existByName", Long.class).setParameter("name", regionName)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- exit-region method");
		}
	}

	public boolean employeeBelongDepartment(final Long managerId, final Long departmentId) throws PersistenceException {
		try {
			LOGGER.info("start -- employee-to-department");
			return em.createNamedQuery("Employees.belongDepartment", Long.class).setParameter("empId", managerId)
					.setParameter("depId", departmentId).getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- employee-to-department");
		}
	}

	public boolean departmentHaveEmployees(final Long departmentId) throws PersistenceException {
		try {
			LOGGER.info("start -- department-have-employees");
			return em.createNamedQuery("Departments.haveEmployees", Long.class).setParameter("id", departmentId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- department-have-employees");
		}
	}

	public boolean departmentIsAvailable(final Long departmentId) throws PersistenceException {
		try {
			LOGGER.info("start -- department-is-available");
			return em.createNamedQuery("Departments.isAvailable", Long.class).setParameter("id", departmentId)
					.getSingleResult() == 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- department-is-available");
		}
	}

	public boolean jobHaveEmployees(final String jobId) throws PersistenceException {
		try {
			LOGGER.info("start -- job-have-employees method");
			return em.createNamedQuery("Jobs.haveEmployees", Long.class).setParameter("id", jobId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- job-have-employees method");
		}
	}

	public boolean locationHaveDepartments(final Long locationId) {
		try {
			LOGGER.info("start -- location-have-departments method");
			return em.createNamedQuery("Locations.haveDepartments", Long.class).setParameter("id", locationId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- location-have-departments method");
		}
	}

	public boolean countryHaveLocations(final String countryId) {
		try {
			LOGGER.info("start -- country-have-locations method");
			return em.createNamedQuery("Countries.haveLocations", Long.class).setParameter("id", countryId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- country-have-locations method");
		}
	}

	public boolean regionHaveCountries(final Long regionId) {
		try {
			LOGGER.info("start -- region-have-countries method");
			return em.createNamedQuery("Regions.haveCountries", Long.class).setParameter("id", regionId)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- region-have-countries method");
		}
	}

	public boolean regionNameIsDuplicate(final String regionName, final Long regionId) throws PersistenceException {
		try {
			LOGGER.info("start -- region-name-duplicate method");
			return em.createNamedQuery("Regions.nameIsDuplicate", Long.class).setParameter("name", regionName)
					.setParameter("id", regionId).getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- region-name-duplicate method");
		}
	}
}
