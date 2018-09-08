package co.com.foundation.crud.jpa.web.api.cors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	private final Logger LOGGER = LogManager.getLogger(CORSFilter.class);

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		LOGGER.info("start -- filter method");
		response.getHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		response.getHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.getHeaders().add("Access-Control-Max-Age", "1209600");
		LOGGER.info("end -- filter method");
	}

}
