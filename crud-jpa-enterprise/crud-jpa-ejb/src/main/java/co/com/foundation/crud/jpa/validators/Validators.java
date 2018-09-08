package co.com.foundation.crud.jpa.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PARAMETER;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD, TYPE, PARAMETER })
public @interface Validators {
	ValidatorType type() default ValidatorType.EMPLOYEE_VALIDATOR;
}
