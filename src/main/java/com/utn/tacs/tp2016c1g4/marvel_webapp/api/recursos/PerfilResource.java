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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

@Path("perfiles")
public class PerfilResource {

	private static final Logger logger = LogManager.getLogger(PerfilResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;

	private Properties params;

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("username") String userName) {
		logger.debug("get invocado");

		FiltroPerfil.Builder filtroBuilder = new FiltroPerfil.Builder();
		filtroBuilder.clear();
		filtroBuilder.setUserName(userName);
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
			// TODO: estos with no van en perfiles, pero es para mostrar como se
			// TODO: podrian usar en otros recursos

			if (with("grupos")) {
				logger.debug("with grupos especificado... buscando");
			}
		}

		opStatus.setStatusCode(status);
		responseBuilder.setOperationStatus(opStatus);

		PerfilGetResponse entityResponse = responseBuilder.build();

		return Response.status(status).entity(entityResponse).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(PerfilPostRequest request) {
		logger.debug("post invocado");

		Perfil p = new Perfil();
		p.setUserName(request.getUsername());
		perfilDao.save(p);

		logger.debug("perfil creado para usuario: " + p.getUserName());

		Response.Status status = Response.Status.OK;

		OperationStatus opStatus = new OperationStatus();
		opStatus.setStatusCode(status);
		opStatus.setMessage("perfil creado exitosamente");

		PerfilPostResponse entityResponse = new PerfilPostResponse();
		entityResponse.setStatus(opStatus);

		return Response.status(status).entity(entityResponse).build();
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
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
