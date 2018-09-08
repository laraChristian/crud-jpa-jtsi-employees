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

import co.com.foundation.crud.jpa.domain.DepartmentRequest;
import co.com.foundation.crud.jpa.domain.DepartmentResponse;
import co.com.foundation.crud.jpa.domain.DepartmentResponse.DepartmentResponseBuilder;
import co.com.foundation.crud.jpa.domain.JobRequest;
import co.com.foundation.crud.jpa.domain.JobResponse;
import co.com.foundation.crud.jpa.domain.JobResponse.JobResponseBuilder;
import co.com.foundation.crud.jpa.dto.DepartmentDTO;
import co.com.foundation.crud.jpa.dto.JobDTO;
import co.com.foundation.crud.jpa.exceptions.DepartmentAvailableException;
import co.com.foundation.crud.jpa.exceptions.DuplicateJobException;
import co.com.foundation.crud.jpa.exceptions.EmployeeNotExistException;
import co.com.foundation.crud.jpa.exceptions.JobAvailableException;
import co.com.foundation.crud.jpa.exceptions.ManagerException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.facade.SystemFacade;

@Stateless
@Path(value = "/administrative-api")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AdministrativeApi {

	private final Logger LOGGER = LogManager.getLogger(AdministrativeApi.class);

	@EJB(beanName = "JobFacade")
	private ModulesFacade<JobRequest, JobDTO> jobFacade;

	@EJB(beanName = "DepartmentsFacade")
	private ModulesFacade<DepartmentRequest, DepartmentDTO> departmentFacade;

	@EJB(beanName = "SystemFacade")
	private SystemFacade systemFacade;

	@GET
	@Path(value = "/list-jobs")
	public Response listJobs() {
		JobResponseBuilder jBuilder = JobResponse.builder();
		try {
			LOGGER.info("start -- list-jobs method");
			return Response.ok().entity(jBuilder.success(true).jobsDTO(jobFacade.listAll()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(jBuilder.success(false).message(e.getMessage()).build())
					.build();
		} finally {
			LOGGER.info("end -- list-jobs method");
		}
	}

	@GET
	@Path(value = "/list-jobs-cmb")
	public Response listJobsCmb() {
		try {
			LOGGER.info("start -- list-jobs method");
			return Response.ok().entity(JobResponse.builder().jobsDTO(systemFacade.listJobsCmb()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(JobResponse.builder().message(e.getMessage()).build())
					.build();
		} finally {
			LOGGER.info("end -- list-jobs method");
		}
	}

	@POST
	@Path(value = "/list-departments")
	public Response listDepartment(final String option) {
		try {
			LOGGER.info("start -- list-departments method");
			return Response.ok().entity(
					DepartmentResponse.builder().departmentsDTO(departmentFacade.listAll()).success(true).build())
					.build();
		} catch (PersistenceException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(DepartmentResponse.builder().message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- list-departments method");
		}
	}

	@POST
	@Path(value = "/create-edit-department")
	public Response createEditDepartment(final DepartmentRequest departmentRequest) {
		LOGGER.info("start -- create-edit-department method");
		DepartmentResponseBuilder dptBuilder = DepartmentResponse.builder();
		try {
			if (departmentRequest.isUpdate()) {
				departmentFacade.update(departmentRequest);
				dptBuilder.message("Department modified successfully");
			} else {
				departmentFacade.create(departmentRequest);
				dptBuilder.message("Department created successfully");
			}
			return Response.ok().entity(dptBuilder.success(true).build()).build();
		} catch (EmployeeNotExistException | ManagerException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(dptBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(dptBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create-edit-department");
		}
	}

	@POST
	@Path(value = "/delete-department")
	public Response deleteDepartment(final DepartmentRequest departmentRequest) {
		LOGGER.info("start -- delete department");
		DepartmentResponseBuilder dpBuilder = DepartmentResponse.builder();
		try {
			departmentFacade.delete(departmentRequest);
			return Response.ok().entity(dpBuilder.success(true).message("Department deleted successfully").build())
					.build();
		} catch (DepartmentAvailableException e) {
			return Response.accepted().entity(dpBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(dpBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete department");
		}
	}

	@POST
	@Path(value = "/create-edit-job")
	public Response createEditJob(final JobRequest request) {
		LOGGER.info("start -- create-edit-job");
		JobResponseBuilder jBuilder = JobResponse.builder();
		try {
			if (request.isUpdate()) {
				jobFacade.update(request);
				jBuilder.message("Job modified successfully");
			} else {
				jobFacade.create(request);
				jBuilder.message("Job created successfully");
			}
			return Response.ok().entity(jBuilder.success(true).build()).build();
		} catch (DuplicateJobException e) {
			return Response.accepted().entity(jBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(jBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("start -- create-edit-job");
		}
	}

	@POST
	@Path(value = "/delete-job")
	public Response deleteJob(final JobRequest request) {
		JobResponseBuilder jBuilder = JobResponse.builder();
		try {
			LOGGER.info("start -- delete method");
			jobFacade.delete(request);
			return Response.ok().entity(jBuilder.message("Job deleted successfully").success(true).build()).build();
		} catch (JobAvailableException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(jBuilder.message(e.getMessage()).success(false).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(jBuilder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- delete method");
		}
	}
}
