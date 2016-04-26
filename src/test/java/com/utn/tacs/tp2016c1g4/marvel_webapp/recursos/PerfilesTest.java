package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PerfilResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;

import static org.junit.Assert.*;

public class PerfilesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(PerfilResource.class);
	}

	@Test
	public void testPerfilesGet() {
		PerfilGetResponse response = target("perfiles/ejemplo").request().get(PerfilGetResponse.class);
		assertEquals("ejemplo", response.getUsername());
	}

	@Test
	public void testPerfilesPost() {
		PerfilPostRequest request = new PerfilPostRequest();
		request.setUsername("ejemplo");
		request.setPassword("asd123");
		PerfilPostResponse response = target("perfiles").request().post(Entity.json(request), PerfilPostResponse.class);
		assertEquals("ejemplo", response.getUsername());
	}
}
