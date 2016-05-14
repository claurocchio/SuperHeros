package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfilBuilder;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

@Path("perfiles")
public class PerfilResource {

	private static final Logger logger = LogManager.getLogger(PerfilResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("username") String userName) {
		logger.debug("get invocado");

		FiltroPerfilBuilder filtroBuilder = new FiltroPerfilBuilder();
		filtroBuilder.clear();
		// filtroBuilder.setUserName(userName);
		Set<FiltroPerfil> filtros = filtroBuilder.build();

		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();

		Perfil perfil = null;
		try {
			perfil = perfilDao.findOne(filtros);

		} catch (ManyResultsException e) {
			// no deberia ocurrir
			logger.error("se obtuvo mas de una coincidencia de usuario");
		}

		if (perfil != null) {
			status = Response.Status.OK;
		} else {
			status = Response.Status.NOT_FOUND;
			opStatus.setMessage("no existe el perfil solicitado");
		}

		opStatus.setCode(status);

		PerfilGetResponse.Builder responseBuilder = new PerfilGetResponse.Builder();

		responseBuilder	.setPerfil(perfil)
						.setOperationStatus(opStatus);

		PerfilGetResponse entityResponse = responseBuilder.build();

		return Response	.status(status)
						.entity(entityResponse)
						.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(PerfilPostRequest request) {
		logger.debug("post invocado");

		Perfil perfil = new Perfil();
		perfil.setId(new Long(1));
		perfil.setUserName(request.getUsername());

		OperationStatus status = new OperationStatus();
		status.setCode(Response.Status.OK);
		status.setMessage("Perfil creado exitosamente");

		PerfilPostResponse response = new PerfilPostResponse();
		response.setStatus(status);
		response.setUsername(perfil.getUserName());

		return Response	.status(Response.Status.OK)
						.entity(response)
						.build();
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}
}
