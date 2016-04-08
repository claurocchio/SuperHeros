package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("favoritos")
public class FavoritoResource {
	
	@GET
	@Produces("application/json")
	public Response get(){
		return Response.status(200).entity("{}").build();
	}
	
}
