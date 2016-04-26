package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("heroes")
public class HeroeResource {

	private static final Logger logger = LogManager.getLogger(HeroeResource.class);

	@GET
	@Produces("application/json")
	public Response get() {
		logger.debug("get invocado");
		return Response.status(200).entity("{}").build();
	}

}
