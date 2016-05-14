package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;
import static javax.ws.rs.core.Response.Status;

@Path("grupos")
public class GrupoResource {

	private static final Logger logger = LogManager.getLogger(GrupoResource.class);

	// private Dao<Grupo> grupoDao;

	@GET
	@Path("/{idGrupo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("idGrupo") Long idGrupo) {
		logger.debug("get invocado");

		GrupoGetResponse response = new GrupoGetResponse();

		// List<SearchFilter> filters = new SearchFilterBuilder().include("id",
		// idGrupo).build();

		Grupo grupo = null;// = grupoDao.findOne();

		Status status = null;

		if (grupo != null) {
			response.getGrupos().add(grupo);
			status = Status.OK;
		} else {
			status = Status.NOT_FOUND;
		}

		return Response.status(status).entity(response).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response nuevo(GrupoPostRequest request) {
		logger.debug("post invocado");

		if (request == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("el request no tiene el formato esperado");
			return Response.status(400).entity(status).build();
		}

		if (request.getName().equals("existente")) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("El grupo ya existe");

			GrupoPostResponse response = new GrupoPostResponse();
			response.setStatus(status);

			return Response.status(202).entity(response).build();
		} else {
			Grupo grupo = new Grupo();
			grupo.setNombre(request.getName());
			grupo.setId(new Long(34));

			OperationStatus status = new OperationStatus();
			status.setSuccess(1);
			status.setMessage("Grupo creado exitosamente");

			GrupoPostResponse response = new GrupoPostResponse();
			response.setStatus(status);
			response.setIdGrupo(grupo.getId());

			return Response.status(201).entity(response).build();
		}
	}

	@PUT
	@Path("/{idGrupo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("idGrupo") Integer idGrupo, GrupoPutRequest request) {
		logger.debug("put invocado");

		if (idGrupo == null || request.getIdPersonaje() == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("no se proporciono un request adecuado");
			return Response.status(400).entity(status).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idGrupo") Integer idGrupo) {
		logger.debug("delete invocado");

		OperationStatus status = new OperationStatus();

		if (idGrupo == null) {
			status.setSuccess(1);
			status.setMessage("no se proporciono un request adecuado");
			return Response.status(400).entity(status).build();
		}

		status.setSuccess(1);
		status.setMessage("se elimino el grupo " + idGrupo + " exitosamente");

		GrupoPostResponse response = new GrupoPostResponse();
		response.setStatus(status);

		return Response.status(200).entity(response).build();
	}

	/*
	 * @Inject public void setGrupoDao(Dao<Grupo> grupoDao) { this.grupoDao =
	 * grupoDao; }
	 */
}
