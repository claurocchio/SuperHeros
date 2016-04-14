package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.grupo.GrupoPostResponse;

@Path("grupos")
public class GrupoResource {

	@POST
	@Consumes("application/json")
	public Response nuevo(GrupoPostRequest request) {
		if (request.getName().equals("existente")) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("El grupo ya existe");

			GrupoPostResponse response = new GrupoPostResponse();
			response.setStatus(status);

			return Response.status(202).entity(response).build();
		} else {
			Grupo grupo = new Grupo(request.getName());
			grupo.setId(34);

			OperationStatus status = new OperationStatus();
			status.setSuccess(1);
			status.setMessage("Grupo creado exitosamente");

			GrupoPostResponse response = new GrupoPostResponse();
			response.setStatus(status);
			response.setIdGrupo(grupo.getId());

			return Response.status(201).entity(response).build();
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
	@Path("/{idGrupo}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response add(@PathParam("idGrupo") Integer idGrupo, GrupoPutRequest request) {
		if (idGrupo == null || request.getIdPersonaje() == null) {
			return Response.status(400).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se añadió al grupo " + idGrupo);

		GrupoPostResponse response = new GrupoPostResponse();
		response.setStatus(status);

		return Response.status(201).entity(response).build();
	}

	@DELETE
	@Path("/{idGrupo}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response delete(@PathParam("idGrupo") Integer idGrupo, GrupoPutRequest request) {
		if (idGrupo == null || request.getIdPersonaje() == null) {
			return Response.status(400).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se eliminó del grupo " + idGrupo);

		GrupoPostResponse response = new GrupoPostResponse();
		response.setStatus(status);

		return Response.status(200).entity(response).build();
	}
}
