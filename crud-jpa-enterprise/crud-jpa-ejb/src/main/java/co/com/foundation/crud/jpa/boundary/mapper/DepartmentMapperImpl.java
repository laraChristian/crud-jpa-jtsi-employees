package co.com.foundation.crud.jpa.boundary.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.boundary.mapper.annotations.MapperType;
import co.com.foundation.crud.jpa.boundary.mapper.annotations.Mappers;
import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import co.com.foundation.crud.jpa.entities.Departments;
import co.com.foundation.crud.jpa.entities.Employees;
import co.com.foundation.crud.jpa.entities.Locations;
import co.com.foundation.crud.jpa.util.CommonUtils;

@Mappers(type = MapperType.DEPARTMENTS)
public class DepartmentMapperImpl implements Mapper<DepartmentDTO, Departments> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentMapperImpl.class);

	@PersistenceContext(unitName = "hr-employees-pu")
	private EntityManager em;

	@Override
	public Departments map(DepartmentDTO departmentDTO) {
		LOGGER.info("start -- map method");
		Departments department = em.find(Departments.class, departmentDTO.getDepartmentId());

		if (CommonUtils.isNull.test(department)) {
			department = new Departments();
			department.setLocationId(em.find(Locations.class, departmentDTO.getLocationId()));
			department.setManagerId(em.find(Employees.class, departmentDTO.getManagerId()));
		} else {
			department.setLocationId(em.find(Locations.class, departmentDTO.getLocationId()));
			department.setManagerId(em.find(Employees.class, departmentDTO.getManagerId()));
		}

		department.setDepartmentName(departmentDTO.getDepartmentName());
		LOGGER.info("end -- map method");
		return department;
	}

}
