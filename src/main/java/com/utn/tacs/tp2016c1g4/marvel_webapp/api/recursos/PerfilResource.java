package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

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

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

@Path("/api/perfiles")
public class PerfilResource {

	private static final Logger logger = LogManager.getLogger(PerfilResource.class);

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("username") String userName) {
		logger.debug("get invocado");
		PerfilGetResponse response = new PerfilGetResponse();
		response.setId(1);
		response.setUsername(userName);
		return Response.status(200).entity(response).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(PerfilPostRequest request) {
		logger.debug("post invocado");

		Perfil perfil = new Perfil();
		perfil.setId(new Long(1));
		perfil.setUsername(request.getUsername());

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("Perfil creado exitosamente");

		PerfilPostResponse response = new PerfilPostResponse();
		response.setStatus(status);
		response.setUsername(perfil.getUsername());

		return Response.status(200).entity(response).build();
	}

}
