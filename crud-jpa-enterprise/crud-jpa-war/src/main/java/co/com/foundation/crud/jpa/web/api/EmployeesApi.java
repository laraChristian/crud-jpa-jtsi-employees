package co.com.foundation.crud.jpa.web.api;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.EmployeeRequest;
import co.com.foundation.crud.jpa.domain.EmployeeResponse;
import co.com.foundation.crud.jpa.domain.EmployeeResponse.EmployeeResponseBuilder;
import co.com.foundation.crud.jpa.domain.LoginRequest;
import co.com.foundation.crud.jpa.domain.LoginResponse;
import co.com.foundation.crud.jpa.domain.LoginResponse.LoginResponseBuilder;
import co.com.foundation.crud.jpa.domain.User;
import co.com.foundation.crud.jpa.dto.EmployeeDTO;
import co.com.foundation.crud.jpa.exceptions.DuplicateMailException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.InvalidCredentialsException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.SystemException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.facade.SystemFacade;

@Stateless
@Path(value = "/employees-api")
@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class EmployeesApi {

	private final Logger LOGGER = LogManager.getLogger(EmployeesApi.class);

	@EJB(beanName = "EmployeesFacade")
	private ModulesFacade<EmployeeRequest, EmployeeDTO> facadeEmployee;

	@EJB(beanName = "SystemFacade")
	private SystemFacade systemFacade;

	@GET
	@Path(value = "/list-employees")
	public Response listEmployees() {
		EmployeeResponseBuilder empBuilder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- list method -- via: {}");
			return Response.status(Status.OK).entity(empBuilder.employeesDTO(facadeEmployee.listAll()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_XML)
					.entity(empBuilder.message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list method -- via: {}");
		}
	}

	@POST
	@Path(value = "/create-edit-employee")
	public Response createOrEditEmployee(final EmployeeRequest employeeRequest) throws SystemException {
		EmployeeResponseBuilder empBuilder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- create-edit method");
			if (employeeRequest.isUpdate()) {
				facadeEmployee.update(employeeRequest);
				return Response.ok().entity(empBuilder.message("Employee modified successfully").success(true).build())
						.build();
			} else {
				facadeEmployee.create(employeeRequest);
				return Response.ok().entity(empBuilder.message("Employee created successfully").success(true).build())
						.build();
			}
		} catch (DuplicateMailException | ManagerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.ACCEPTED).entity(empBuilder.message(e.getMessage()).success(false).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(empBuilder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- create-edit method");
		}
	}

	@POST
	@Path(value = "/delete-employee")
	public Response deleteEmployee(final EmployeeRequest request) {
		EmployeeResponseBuilder empBuilder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- delete method");
			facadeEmployee.delete(request);
			return Response.ok().entity(empBuilder.success(true).message("Employee deleted successfully").build())
					.build();
		} catch (EmployeeNotExistException | ManagerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.ACCEPTED).entity(empBuilder.message(e.getMessage()).success(false).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(empBuilder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	@POST
	@Path(value = "/loggin")
	public Response loggin(final LoginRequest request) {
		LoginResponseBuilder lgnBuilder = LoginResponse.builder();
		try {
			LOGGER.info("start -- loggin-method for user: {}", request.getUser().getEmail());
			User user = systemFacade.loggin(request);
			return Response.ok().entity(lgnBuilder.user(user).loggedIn(user != null).build()).build();
		} catch (InvalidCredentialsException e) {
			return Response.status(Status.OK).entity(lgnBuilder.message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(lgnBuilder.message(e.getMessage()).build())
					.build();
		} finally {
			LOGGER.info("end -- loggin-method");
		}
	}

	@GET
	@Path(value = "/list-managers-cmb")
	public Response listManagersCmb() {
		EmployeeResponseBuilder empBuilder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- list-managers");
			return Response.ok().entity(empBuilder.success(true).employeesDTO(systemFacade.listManagersCmb()).build())
					.build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(empBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-managers");
		}
	}

}
