package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

@Path("/perfiles")
public class PerfilResource {

	private static final Logger logger = LogManager.getLogger(PerfilResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Grupo, FiltroGrupo> grupoDao;

	private Properties params;

	@GET
	@Path("/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("userid") Long userId) {
		logger.debug("get invocado");

		FiltroPerfil.Builder filtroBuilder = new FiltroPerfil.Builder();
		filtroBuilder.clear();
		filtroBuilder.setId(userId);
		Set<FiltroPerfil> filtros = filtroBuilder.build();

		logger.debug("filtrando por: " + filtros);

		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();
		PerfilGetResponse.Builder responseBuilder = new PerfilGetResponse.Builder();

		Perfil perfil = null;
		try {
			perfil = perfilDao.findOne(filtros);
			responseBuilder.setPerfil(perfil);
			status = Response.Status.OK;
		} catch (ManyResultsException e) {
			// no deberia ocurrir
			logger.error("se obtuvo mas de una coincidencia de usuario");
		}

		if (perfil == null) {
			status = Response.Status.NOT_FOUND;
			opStatus.setMessage("no existe el perfil solicitado");
		} else {
			if (with("grupos")) {
				logger.debug("with grupos especificado... buscando");
				responseBuilder.setGrupoDao(grupoDao);
				responseBuilder.setExpandirGrupos(true);
			}
		}

		opStatus.setStatusCode(status);
		responseBuilder.setOperationStatus(opStatus);

		PerfilGetResponse entityResponse = responseBuilder.build();

		return Response.status(status).entity(entityResponse).build();
	}

	private boolean isValidRequest(PerfilPostRequest request) {
		boolean isValid = true;

		isValid = isValid && request.getUsername() != null && !request.getUsername().isEmpty();
		isValid = isValid && request.getPassword() != null && !request.getPassword().isEmpty();
		isValid = isValid && request.getEmail() != null && !request.getEmail().isEmpty();

		return isValid;
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
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
