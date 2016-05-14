package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PerfilResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

import static org.junit.Assert.*;

public class PerfilesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(PerfilResource.class).register(GuiceInMemoryFeature.class);
	}

	@Test
	public void testPerfilesGet() {
		Response response = target("perfiles/ejemplo").request().get(Response.class);
		assertEquals("no encuentra el perfil por no ser ingresado aun", 404, response.getStatus());
	}

	@Test
	public void testPerfilesPost() {
		PerfilPostRequest request = new PerfilPostRequest();
		request.setUsername("ejemplo");
		request.setPassword("asd123");
		Response response = target("perfiles").request().post(Entity.json(request), Response.class);
		PerfilPostResponse perfilResponse = response.readEntity(PerfilPostResponse.class);

		// assertEquals("creacion exitosa de usuario - headers",
		// response.getStatus(), Response.Status.OK.getStatusCode());
		// assertEquals("creacion exitosa de usuario",
		// perfilResponse.getStatus().getCode(), 200);
	}
}
