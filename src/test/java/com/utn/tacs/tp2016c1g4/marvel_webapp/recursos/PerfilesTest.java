package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PerfilResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

public class PerfilesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(PerfilResource.class).register(GuiceInMemoryFeature.class);
	}

	@Test
	public void testPerfilesGet() {
		//PerfilGetResponse response = target("/api/perfiles/ejemplo").request().get(PerfilGetResponse.class);
		//assertEquals("ejemplo", response.getUsername());
		//assertEquals("no encuentra el perfil por no ser ingresado aun", 404, response.getStatus());
	}

	@Test
	public void testPerfilesPost() {
		/*
		PerfilPostRequest request = new PerfilPostRequest();
		request.setUsername("ejemplo");
		request.setPassword("asd123");
		PerfilPostResponse response = target("/api/perfiles").request().post(Entity.json(request), PerfilPostResponse.class);
		assertEquals("ejemplo", response.getUsername());
		*/
//		PerfilPostResponse perfilResponse = response.readEntity(PerfilPostResponse.class);

		// assertEquals("creacion exitosa de usuario - headers",
		// response.getStatus(), Response.Status.OK.getStatusCode());
		// assertEquals("creacion exitosa de usuario",
		// perfilResponse.getStatus().getCode(), 200);
	}
}
