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
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoDeleteResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPutResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Path("/grupos")
public class GrupoResource {

	private static final Logger logger = LogManager.getLogger(GrupoResource.class);

	private Dao<Grupo, FiltroGrupo> grupoDao;
	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Personaje, FiltroPersonaje> personajeDao;

	private Properties params;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGrupos() {
		logger.debug("get invocado");

		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();

		Collection<Grupo> grupos = null;

		if (params.containsKey("idUsuario")) {

			grupos = new HashSet<Grupo>();

			FiltroPerfil.Builder filtroPerfilBuilder = new FiltroPerfil.Builder();
			filtroPerfilBuilder.setId(params.getProperty("idUsuario"));
			Set<FiltroPerfil> filtroPerfil = filtroPerfilBuilder.build();

			Perfil p = perfilDao.findOne(filtroPerfil);

			Collection<Long> idGrupos = p.getIdGrupos();

			for (Long idGrupo : idGrupos) {
				FiltroGrupo.Builder filtroGrupoBuilder = new FiltroGrupo.Builder();
				filtroGrupoBuilder.clear();
				filtroGrupoBuilder.setId(idGrupo);
				Set<FiltroGrupo> filtroGrupo = filtroGrupoBuilder.build();
				Grupo grupo = grupoDao.findOne(filtroGrupo);

				if (grupo != null)
					grupos.add(grupo);
			}
		} else {
			grupos = grupoDao.getAll();
		}

		GrupoGetResponse.Builder responseBuilder = new GrupoGetResponse.Builder();

		if (with("personajes")) {
			responseBuilder.setPersonajeDao(personajeDao);
			responseBuilder.setExpandirPersonajes(true);

			if (params.containsKey("img-variant")) {
				String[] variants = params.getProperty("img-variant").split(",");

				for (String variant : variants)
					responseBuilder.addVarianteImagen(variant);
			}

			if (params.containsKey("img-extension")) {
				responseBuilder.setExtensionImagen(params.getProperty("img-extension"));
			}
		}

		status = Status.OK;
		opStatus.setStatusCode(status);
		responseBuilder.setGrupos(grupos);
		responseBuilder.setOperationStatus(opStatus);

		GrupoGetResponse entityResponse = responseBuilder.build();

		return Response.status(status).entity(entityResponse).build();
	}

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
			if (with("personajes")) {
				responseBuilder.setPersonajeDao(personajeDao);
				responseBuilder.setExpandirPersonajes(true);

				if (params.containsKey("img-variant")) {
					String[] variants = params.getProperty("img-variant").split(",");

					for (String variant : variants)
						responseBuilder.addVarianteImagen(variant);
				}

				if (params.containsKey("img-extension")) {
					responseBuilder.setExtensionImagen(params.getProperty("img-extension"));
				}
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
		Response.Status status = null;

		OperationStatus opStatus = new OperationStatus();

		if (!IsValidRequest(request)) {
			status = Status.BAD_REQUEST;
			message = "el request no tiene el formato esperado";
			logger.debug(message);
			logger.debug(request);
			opStatus.setStatusCode(status);
			opStatus.setMessage(message);
			response.setStatus(opStatus);
			return Response.status(status).entity(response).build();
		}

		Perfil perfil = null;

		if ((perfil = getPerfilDeUsuario(request.getIdUsuario())) == null) {
			status = Status.NOT_FOUND;
			message = "no existe un perfil asociado a ese usuario";
			logger.debug(message);
			logger.debug(request);
			opStatus.setStatusCode(status);
			opStatus.setMessage(message);
			response.setStatus(opStatus);
			return Response.status(status).entity(response).build();
		}

		FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
		filtroBuilder.setName(request.getName());

		Grupo grupo = null;
		try {
			grupo = grupoDao.findOne(filtroBuilder.build());
		} catch (ManyResultsException e) {
		}

		if (grupo != null) {
			status = Status.CONFLICT;
			message = "ya existe este grupo";
			logger.debug(message);
			logger.debug(request);
			opStatus.setStatusCode(status);
			opStatus.setMessage(message);
			response.setStatus(opStatus);
			return Response.status(status).entity(response).build();
		}

		grupo = new Grupo();
		grupo.setNombre(request.getName());

		try {
			grupoDao.save(grupo);
			logger.debug("grupo %s[id:%d] creado", grupo.getNombre(), grupo.getId());
			logger.debug("asignando grupo %d a perfil %d", grupo.getId(), perfil.getId());
			perfil.getIdGrupos().add(grupo.getId());
			perfilDao.update(perfil);
		} catch (RuntimeException e) {
			status = Status.INTERNAL_SERVER_ERROR;
			message = "ocurrió un problema al guardar el grupo... reintente más tarde";
			logger.debug(message);
			logger.debug(request);
			opStatus.setStatusCode(status);
			opStatus.setMessage(message);
			response.setStatus(opStatus);
			return Response.status(status).entity(response).build();
		}

		response.setIdGrupo(grupo.getId());
		response.setNombre(grupo.getNombre());

		status = Status.OK;
		message = "grupo creado exitosamente";

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

		if (IsValidRequest(request)) {
			FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
			filtroBuilder.clear();
			filtroBuilder.setId(idGrupo);
			Set<FiltroGrupo> filtros = filtroBuilder.build();

			grupo = grupoDao.findOne(filtros);
			if (grupo == null) {
				status = Response.Status.NOT_FOUND;
				opStatus.setMessage("El grupo " + idGrupo + "no existe");
			} else {
				if (request.getIdPersonajes() != null) {
					logger.debug("listado personajes provisto,  limpiando grupo y agregando personajes para grupo: "
							+ idGrupo);
					grupo.getPersonajes().clear();
					for (Long idPersonaje : request.getIdPersonajes()) {
						if (grupo.getPersonajes().add(idPersonaje)) {
							logger.debug("personaje " + idPersonaje + " añadido al grupo " + grupo.getId());
						}
					}
				}

				if (request.getNombre() != null) {
					logger.debug("nombre provisto,  cambiando nombre de grupo " + grupo.getId());
					grupo.setNombre(request.getNombre());
				}

				grupoDao.update(grupo);

				opStatus.setMessage("grupo " + grupo.getId() + " actualizado con exito");
				status = Response.Status.OK;

			}
		} else {
			opStatus.setMessage("no se proporcionó un request adecuado");
			status = Response.Status.BAD_REQUEST;
		}

		GrupoPutResponse response = new GrupoPutResponse();
		response.setStatus(opStatus);

		return Response.status(status).entity(response).build();
	}

	private boolean IsValidRequest(GrupoPutRequest request) {
		boolean isValid = false;
		isValid = isValid || request.getIdPersonajes() != null;
		isValid = isValid || request.getNombre() != null;
		return isValid;
	}

	private boolean IsValidRequest(GrupoPostRequest request) {
		boolean isValid = true;
		isValid = isValid && request.getIdUsuario() != null;
		isValid = isValid && request.getName() != null;
		return isValid;
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

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}

	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
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

	private Perfil getPerfilDeUsuario(Long idUsuario) {

		Perfil perfil = null;

		FiltroPerfil.Builder filtroPerfilBuilder = new FiltroPerfil.Builder();
		filtroPerfilBuilder.setId(idUsuario);
		try {
			perfil = perfilDao.findOne(filtroPerfilBuilder.build());
		} catch (ManyResultsException e) {
		}

		return perfil;
	}
}
