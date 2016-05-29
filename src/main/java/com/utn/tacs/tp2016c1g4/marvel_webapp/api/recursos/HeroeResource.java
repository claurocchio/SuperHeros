package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Repository;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.heroe.HeroePostRequest;

@Path("/api/heroes")
public class HeroeResource {

	private static final Logger logger = LogManager.getLogger(HeroeResource.class);

	private Repository<Personaje> heroeContainer;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		return Response.status(Response.Status.OK).entity("{}").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(HeroePostRequest request) {
		Personaje p = new Personaje();
		p.setId(request.getId());
		p.setName(request.getName());
		heroeContainer.add(p);
		return Response.status(Response.Status.OK).entity(heroeContainer).build();
	}

	public Repository<Personaje> getHeroeContainer() {
		return heroeContainer;
	}

	@Inject
	public void setHeroeContainer(Repository<Personaje> heroeContainer) {
		this.heroeContainer = heroeContainer;
	}

}
