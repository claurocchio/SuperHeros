package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPostResponse;

@Path("favoritos")
public class FavoritoResource {

	@GET
	@Produces("application/json")
	public Set<Personaje> get() {
		Set<Personaje> favoritos = new HashSet<Personaje>();
		Personaje hulk = new Personaje(1, "Hulk");
		Personaje thor = new Personaje(2, "Thor");
		favoritos.add(hulk);
		favoritos.add(thor);
		return favoritos;
	}

	@POST
	@Consumes("application/json")
	public Response add(FavoritoPostRequest request) {
		if (request.getIdPersonaje() == null) {
			return Response.status(400).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se añadió a favoritos");

		FavoritoPostResponse response = new FavoritoPostResponse();
		response.setStatus(status);

		return Response.status(201).entity(response).build();
	}

}
