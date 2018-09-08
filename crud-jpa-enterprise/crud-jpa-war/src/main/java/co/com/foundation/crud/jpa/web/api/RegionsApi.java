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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.crud.jpa.domain.CountryRequest;
import co.com.foundation.crud.jpa.domain.CountryResponse;
import co.com.foundation.crud.jpa.domain.CountryResponse.CountryResponseBuilder;
import co.com.foundation.crud.jpa.domain.LocationRequest;
import co.com.foundation.crud.jpa.domain.LocationResponse;
import co.com.foundation.crud.jpa.domain.RegionRequest;
import co.com.foundation.crud.jpa.domain.LocationResponse.LocationResponseBuilder;
import co.com.foundation.crud.jpa.domain.RegionResponse;
import co.com.foundation.crud.jpa.domain.RegionResponse.RegionResponseBuilder;
import co.com.foundation.crud.jpa.dto.CountryDTO;
import co.com.foundation.crud.jpa.dto.LocationDTO;
import co.com.foundation.crud.jpa.dto.RegionDTO;
import co.com.foundation.crud.jpa.exceptions.DuplicateRegionException;
import co.com.foundation.crud.jpa.exceptions.PersistenceException;
import co.com.foundation.crud.jpa.exceptions.RegionAvailableException;
import co.com.foundation.crud.jpa.facade.ModulesFacade;
import co.com.foundation.crud.jpa.facade.SystemFacade;

@Stateless
@Path(value = "/regions-api")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RegionsApi {

	private final Logger LOGGER = LogManager.getLogger(RegionsApi.class);

	@EJB(beanName = "SystemFacade")
	private SystemFacade systemFacade;

	@EJB(beanName = "LocationFacade")
	private ModulesFacade<LocationRequest, LocationDTO> locationFacade;

	@EJB(beanName = "RegionFacade")
	ModulesFacade<RegionRequest, RegionDTO> regionFacade;

	@EJB(beanName = "CountryFacade")
	private ModulesFacade<CountryRequest, CountryDTO> countryFacade;

	@GET
	@Path(value = "/list-locations-cmb")
	public Response listLocationsCmb() {
		LocationResponseBuilder lctbuilder = LocationResponse.builder();
		try {
			LOGGER.info("start -- list-to-cmb");
			return Response.ok().entity(lctbuilder.success(true).locationsDTO(systemFacade.listLocationsCmb()).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(lctbuilder.success(false).build()).build();
		} finally {
			LOGGER.info("end -- list-to-cmb");
		}
	}

	@GET
	@Path(value = "/list-countries-cmb")
	public Response listCountriesCmb() {
		CountryResponseBuilder ctBuilder = CountryResponse.builder();
		try {
			LOGGER.info("start -- list-countries-cmb");
			return Response.ok().entity(ctBuilder.success(true).countriesDTO(systemFacade.listCountriesCmb()).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(ctBuilder.success(false).build()).build();
		} finally {
			LOGGER.info("end -- list-countries-cmb");
		}
	}

	@GET
	@Path(value = "/list-regions.cmb")
	public Response listRegionsCmb() {
		LOGGER.info("star -- list-regions-cmb method ");
		RegionResponseBuilder rBuilder = RegionResponse.builder();
		try {
			return Response.ok().entity(rBuilder.regionsDTO(systemFacade.listRegionsCmb()).success(true).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(rBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-regions-cmb method ");
		}
	}

	@POST
	@Path(value = "/create-edit-location")
	public Response createEditLocation(final LocationRequest request) {
		LocationResponseBuilder lBuilder = LocationResponse.builder();
		try {
			LOGGER.info("start -- create-edit-location method ");
			if (request.isUpdate()) {
				locationFacade.update(request);
				return Response.ok().entity(lBuilder.success(true).message("Location modified successfully").build())
						.build();
			} else {
				locationFacade.create(request);
				return Response.ok().entity(lBuilder.success(true).message("Location created successfully").build())
						.build();
			}
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(lBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create-edit-location method ");
		}
	}

	@POST
	@Path(value = "/delete-location")
	public Response deleteLocation(final LocationRequest request) {
		LocationResponseBuilder lrBuilder = LocationResponse.builder();
		try {
			LOGGER.info("start -- delete method");
			locationFacade.delete(request);
			return Response.ok().entity(lrBuilder.success(true).message("Location was deleted successfully").build())
					.build();
		} catch (RegionAvailableException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(lrBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(lrBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

	@GET
	@Path(value = "/list-locations")
	public Response listLocations() {
		LocationResponseBuilder lctbuilder = LocationResponse.builder();
		try {
			LOGGER.info("start -- list-to-cmb");
			return Response.ok().entity(lctbuilder.success(true).locationsDTO(locationFacade.listAll()).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(lctbuilder.success(false).build()).build();
		} finally {
			LOGGER.info("end -- list-to-cmb");
		}
	}

	@POST
	@Path(value = "/create-edit-country")
	public Response createEditCountry(final CountryRequest request) {
		CountryResponseBuilder cBuiler = CountryResponse.builder();
		try {

			if (request.isUpdate()) {
				countryFacade.update(request);
				cBuiler.message("Country was modified successfully");
			} else {
				countryFacade.create(request);
				cBuiler.message("Country was created successfully");
			}

			return Response.ok().entity(cBuiler.success(true).build()).build();
		} catch (DuplicateRegionException e) {
			return Response.accepted().entity(cBuiler.message(e.getMessage()).success(false).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(cBuiler.message(e.getMessage()).success(false).build()).build();
		}
	}

	@GET
	@Path(value = "/list-countries")
	public Response listCountries() {
		LOGGER.info("start -- list-all method");
		CountryResponseBuilder cBuilder = CountryResponse.builder();
		try {
			return Response.ok().entity(cBuilder.countriesDTO(countryFacade.listAll()).success(true).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.ok().entity(cBuilder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@POST
	@Path(value = "/delete-country")
	public Response deleteCountry(final CountryRequest request) {
		CountryResponseBuilder cResponse = CountryResponse.builder();
		try {
			countryFacade.delete(request);
			return Response.ok().entity(cResponse.message("Country was deleted successfully").success(true).build())
					.build();
		} catch (RegionAvailableException e) {
			return Response.accepted().entity(cResponse.message(e.getMessage()).success(false).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(cResponse.message(e.getMessage()).success(false).build()).build();
		}
	}

	@GET
	@Path(value = "/list-regions")
	public Response listRegions() {
		LOGGER.info("start -- list-all method");
		RegionResponseBuilder rBuilder = RegionResponse.builder();
		try {
			return Response.ok().entity(rBuilder.regionsDTO(regionFacade.listAll()).success(true).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(rBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@POST
	@Path(value = "/create-edit-region")
	public Response createEditRegion(final RegionRequest request) {
		RegionResponseBuilder rBuilder = RegionResponse.builder();
		try {
			LOGGER.info("start -- create-region method");
			if (request.isUpdate()) {
				regionFacade.update(request);
				return Response.ok().entity(rBuilder.message("Region was mofied successfully").success(true).build())
						.build();
			} else {
				regionFacade.create(request);
				return Response.ok().entity(rBuilder.message("Region was created successfully").success(true).build())
						.build();
			}
		} catch (DuplicateRegionException e) {
			return Response.accepted().entity(rBuilder.message(e.getMessage()).success(false).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(rBuilder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- create-region method");
		}
	}

	@POST
	@Path(value = "/delete-region")
	public Response deleteRegion(final RegionRequest request) {
		RegionResponseBuilder rBuilder = RegionResponse.builder();
		try {
			LOGGER.info("start -- delete-region method");
			regionFacade.delete(request);
			return Response.ok().entity(rBuilder.success(true).message("The region was deleted successfully").build())
					.build();
		} catch (RegionAvailableException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(rBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(rBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete-region method");
		}
	}

}
