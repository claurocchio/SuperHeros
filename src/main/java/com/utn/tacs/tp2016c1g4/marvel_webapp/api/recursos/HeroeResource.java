package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Container;

@Path("heroes")
public class HeroeResource {

	private static final Logger logger = LogManager.getLogger(HeroeResource.class);

	private Container<Personaje> heroeContainer;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");
		
		return Response.status(Response.Status.OK).entity("{}").build();
	}

	public Container<Personaje> getHeroeContainer() {
		return heroeContainer;
	}

	@Inject
	public void setHeroeContainer(Container<Personaje> heroeContainer) {
		this.heroeContainer = heroeContainer;
	}

}
