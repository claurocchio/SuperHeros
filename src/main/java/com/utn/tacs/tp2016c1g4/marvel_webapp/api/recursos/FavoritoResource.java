package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.*;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPostResponse;


@Path("/api/favoritos")
public class FavoritoResource {

	private static final Logger logger = LogManager.getLogger(FavoritoResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		List<Personaje> favoritos = new ArrayList<Personaje>();
		favoritos.add(new Personaje(new Long(1), "Hulk"));
		favoritos.add(new Personaje(new Long(2), "Thor"));

		FavoritoGetResponse response = new FavoritoGetResponse();
		response.setFavoritos(favoritos);

		return Response.status(200).entity(response).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(FavoritoPostRequest request) {
		logger.debug("post invocado");

		if (request == null || request.getIdPersonaje() == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("no se especifico id personaje o request alguno");
			logger.debug(status);
			return Response.status(400).entity(status).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se añadió a favoritos");

		FavoritoPostResponse response = new FavoritoPostResponse();
		response.setStatus(status);

		return Response.status(201).entity(response).build();
	}

	// get favoritos de un usuario
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("userId") Integer userId) {
		logger.debug("get invocado");

		List<Personaje> favoritosDeUser = new ArrayList<Personaje>();
		favoritosDeUser.add(new Personaje(new Long(1), "Hulk"));
		favoritosDeUser.add(new Personaje(new Long(2), "Thor"));

		FavoritoGetResponse response = new FavoritoGetResponse();
		response.setFavoritosPorUser(userId,favoritosDeUser);

		return Response.status(200).entity(response).build();
	}
	
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("userId") Integer userId, FavoritoPutRequest request) {
		logger.debug("put invocado");

		if (userId == null || request.getIdPersonaje() == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("no se proporciono un request adecuado");
			return Response.status(400).entity(status).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se añadió a los favoritos del usuario id " + userId);

		FavoritoPostResponse response = new FavoritoPostResponse();
		response.setStatus(status);

		return Response.status(201).entity(response).build();
	}

	
}
