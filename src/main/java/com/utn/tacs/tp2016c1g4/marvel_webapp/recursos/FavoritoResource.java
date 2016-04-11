package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;

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

	@PUT
	@Path("/add/{idPersonaje}")
	@Consumes("application/json")
	public Response add(@PathParam("idPersonaje") Integer idPersonaje) {
		if (idPersonaje == null) {
			return Response.status(400).build();
		}
		return Response.status(201).entity("El personaje " + idPersonaje + " se añadió a favoritos").build();
	}
}
