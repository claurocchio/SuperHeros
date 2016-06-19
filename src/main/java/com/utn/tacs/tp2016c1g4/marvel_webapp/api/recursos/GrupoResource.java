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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoDeleteResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPutResponse;

import java.util.Properties;
import java.util.Set;

@Path("/grupos")
public class GrupoResource {

	private static final Logger logger = LogManager.getLogger(GrupoResource.class);

	private Dao<Grupo, FiltroGrupo> grupoDao;

	private Properties params;

	@GET
	@Path("/{idGrupo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("idGrupo") Long idGrupo) {
		logger.debug("get invocado");

		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();
		FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
		filtroBuilder.clear();
		filtroBuilder.setId(idGrupo);
		Set<FiltroGrupo> filtros = filtroBuilder.build();

		GrupoGetResponse.Builder responseBuilder = new GrupoGetResponse.Builder();

		Grupo grupo = null;// = grupoDao.findOne();

		try {
			grupo = grupoDao.findOne(filtros);
			responseBuilder.setGrupo(grupo);
			status = Response.Status.OK;
		} catch (ManyResultsException e) {
			// no deberia ocurrir
			logger.error("se obtuvo mas de una coincidencia de usuario");
		}

		if (grupo == null) {
			status = Response.Status.NOT_FOUND;
			opStatus.setMessage("no existe el grupo solicitado");
		} else {
			// TODO: estos with no van en perfiles, pero es para mostrar como se
			// TODO: podrian usar en otros recursos

			if (with("personajes")) {
				logger.debug("with personajes especificado... buscando");
			}
		}

		opStatus.setStatusCode(status);
		responseBuilder.setOperationStatus(opStatus);

		GrupoGetResponse entityResponse = responseBuilder.build();

		return Response.status(status).entity(entityResponse).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response nuevo(GrupoPostRequest request) {
		logger.debug("post invocado");

		GrupoPostResponse response = new GrupoPostResponse();
		String message = "";
		Response.Status status;

		if (request.getName() == null) {
			status = Status.BAD_REQUEST;
			message = "el request no tiene el formato esperado";
		} else {
			FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
			filtroBuilder.setName(request.getName());

			Grupo grupo = null;
			try {
				grupo = grupoDao.findOne(filtroBuilder.build());
			} catch (ManyResultsException e) {
			}

			if (grupo == null) {

				grupo = new Grupo();
				grupo.setNombre(request.getName());
				grupoDao.save(grupo);

				response.setIdGrupo(grupo.getId());
				response.setNombre(grupo.getNombre());

				status = Status.OK;
				message = "grupo creado exitosamente";
				logger.debug("grupo creado con nombre: " + grupo.getNombre());
			} else {
				status = Status.CONFLICT;
				message = "ya existe este grupo";

				logger.debug("conflicto al crear grupo ya existente: " + request.getName());
			}

		}

		OperationStatus opStatus = new OperationStatus();
		opStatus.setStatusCode(status);
		opStatus.setMessage(message);

		response.setStatus(opStatus);

		return Response.status(status).entity(response).build();
	}

	@PUT
	@Path("/{idGrupo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("idGrupo") Long idGrupo, GrupoPutRequest request) {
		logger.debug("put invocado");

		OperationStatus opStatus = new OperationStatus();
		Response.Status status = null;
		Grupo grupo = null;

		if (idGrupo == null || request.getIdPersonaje() == null) {

			opStatus.setMessage("no se proporciono un request adecuado");
			status = Response.Status.BAD_REQUEST;
		} else {
			FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
			filtroBuilder.clear();
			filtroBuilder.setId(idGrupo);
			Set<FiltroGrupo> filtros = filtroBuilder.build();

			grupo = grupoDao.findOne(filtros);
			if (grupo == null) {
				status = Response.Status.NOT_FOUND;
				opStatus.setMessage("El grupo " + idGrupo + "no existe");
			} else {
				grupo.addPersonaje(request.getIdPersonaje());
				status = Response.Status.OK;
				opStatus.setMessage("El personaje " + request.getIdPersonaje() + " se añadió al grupo " + idGrupo);
			}
		}

		GrupoPutResponse response = new GrupoPutResponse();
		response.setStatus(opStatus);

		return Response.status(status).entity(response).build();
	}

	@DELETE
	@Path("/{idGrupo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idGrupo") Long idGrupo) {
		logger.debug("delete invocado");

		OperationStatus opStatus = new OperationStatus();
		Response.Status status = null;
		Grupo grupo = null;

		if (idGrupo == null) {
			status = Response.Status.BAD_REQUEST;
			opStatus.setMessage("no se proporciono un request adecuado");
			// return Response.status(400).entity(status).build();
		} else {
			FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
			filtroBuilder.clear();
			filtroBuilder.setId(idGrupo);
			Set<FiltroGrupo> filtros = filtroBuilder.build();

			grupo = grupoDao.findOne(filtros);
			// TODO: borrar grupo

			status = Response.Status.OK;
			opStatus.setMessage("se elimino el grupo " + idGrupo + " exitosamente");
		}

		GrupoDeleteResponse response = new GrupoDeleteResponse();
		response.setStatus(opStatus);

		return Response.status(status).entity(response).build();
	}

	@Inject
	public void setGrupoDao(Dao<Grupo, FiltroGrupo> grupoDao) {
		this.grupoDao = grupoDao;
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		logger.debug("catcheando uri info en perfiles");

		this.params = new Properties();
		for (String key : uriInfo.getQueryParameters().keySet()) {
			this.params.setProperty(key, uriInfo.getQueryParameters().getFirst(key));
			logger.debug("query param[ " + key + " = " + this.params.getProperty(key) + " ]");
		}
	}

	private boolean with(String adicionalRequerido) {
		String with = params.getProperty("with");
		return with != null ? with.matches(".*?,?" + adicionalRequerido + ",?.*?") : false;
	}
}
