package co.com.foundation.crud.jpa.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Validators(type = ValidatorType.EMPLOYEE_VALIDATOR)
public class EmployeesValidator {

	private final Logger LOGGER = LogManager.getLogger(EmployeesValidator.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	public boolean isManager(long id) throws PersistenceException {
		try {
			LOGGER.info("start -- is-manager method");
			return em.createNamedQuery("Employees.isManager", Long.class).setParameter("key", id).getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- is-manager method");
		}
	}

	public boolean mailExist(final String mail) throws PersistenceException {
		try {
			LOGGER.info("start -- mail-exist method");
			return em.createNamedQuery("Employees.mailExist", Long.class).setParameter("mail", mail)
					.getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- mail-exist method");
		}
	}

	public boolean exist(final Long id) throws PersistenceException {
		try {
			LOGGER.info("start -- exist method");
			return em.createNamedQuery("Employees.exist", Long.class).setParameter("id", id).getSingleResult() > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- exist method");
		}
	}

	public boolean findJobHistories(long id) {
		try {
			LOGGER.info("end -- find-job-histories method");
			Long count = em.createNamedQuery("JobHistory.employeeHaveHistories", Long.class).setParameter("key", id)
					.getSingleResult();
			return count > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- find-job-histories method");
		}
	}

}
