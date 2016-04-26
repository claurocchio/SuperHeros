package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPostResponse;

@Path("favoritos")
public class FavoritoResource {

	private static final Logger logger = LogManager.getLogger(FavoritoResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		List<Personaje> favoritos = new ArrayList<Personaje>();
		favoritos.add(new Personaje(1, "Hulk"));
		favoritos.add(new Personaje(2, "Thor"));

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

}
