package co.com.foundation.crud.jpa.util;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import co.com.foundation.crud.jpa.dto.EmployeeDTO;

public interface CommonUtils {

	Predicate<Object> isNull = (Object object) -> Objects.isNull(object);
	Predicate<EmployeeDTO> MANAGER_HAVE_A_DEPARTMENT = (dto) -> dto.getDepartmentId() > 0;
	BiPredicate<Long, Long> ARE_DIFERENTS = (idA, Idb) -> idA != Idb;
	Long emptyManager = null;
}
