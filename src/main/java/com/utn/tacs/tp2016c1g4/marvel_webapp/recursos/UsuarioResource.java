package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("usuarios")
public class UsuarioResource {

	@POST
	@Path("/nuevo")
	@Consumes("application/json")
	public Response nuevo(String user, String pass) {
		if (user.equals("existente")) {
			return Response.status(202).entity("El usuario ya existe").build();
		} else {
			return Response.status(201).entity("Usuario creado").build();
		}
	}

}
