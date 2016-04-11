package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;

@Path("grupos")
public class GrupoResource {

	@POST
	@Path("/nuevo")
	@Consumes("application/json")
	public Response nuevo(String nombre) {
		if (nombre.equals("existente")) {
			return Response.status(202).entity("El grupo ya existe").build();
		} else {
			return Response.status(201).entity("Grupo creado").build();
		}
	}

	@GET
	@Path("/{idGrupo}")
	@Consumes("application/json")
	@Produces("application/json")
	public Grupo get(@PathParam("idGrupo") Integer idGrupo) {
		Grupo grupo = new Grupo(1, "2 fantásticos");
		Personaje hulk = new Personaje(1, "Hulk");
		Personaje thor = new Personaje(2, "Thor");
		grupo.addPersonaje(hulk);
		grupo.addPersonaje(thor);
		return grupo;
	}

	@PUT
	@Path("/add/{idGrupo}/{idPersonaje}")
	@Consumes("application/json")
	public Response add(@PathParam("idGrupo") Integer idGrupo, @PathParam("idPersonaje") Integer idPersonaje) {
		if (idGrupo == null || idPersonaje == null) {
			return Response.status(400).build();
		}
		return Response.status(201).entity("El personaje " + idPersonaje + " se añadió al grupo " + idGrupo).build();
	}
}
