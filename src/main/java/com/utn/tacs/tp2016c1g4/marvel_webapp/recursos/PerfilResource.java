package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.PerfilPostResponse;

@Path("perfiles")
public class PerfilResource {

	@GET
	@Path("/{username}")
	@Produces("application/json")
	public Response get( @PathParam("username") String userName){
		PerfilGetResponse response = new PerfilGetResponse();
		response.setId(1);
		response.setUsername(userName);
		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response post(PerfilPostRequest request){
		Perfil perfil = new Perfil();
		perfil.setId(1);
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
