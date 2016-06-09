package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
		Response response = null;
		response = target("/api/perfiles/ejemplo").request().get(Response.class);
		assertEquals("el perfil aun no fue creado y debe tirar not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// creo un perfil

		PerfilPostRequest postRequest = new PerfilPostRequest();
		postRequest.setUsername("ejemplo");
		postRequest.setPassword("123");
		postRequest.setEmail("test@test.com");

		response = target("/api/perfiles/ejemplo").request().post(Entity.json(postRequest), Response.class);
		assertEquals("url con nombre al final debe tirar not allowed al hacer post",
				Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());

		response = target("/api/perfiles").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("/api/perfiles/ejemplo").request().get(Response.class);
		assertEquals("obtencion de perfil existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		PerfilGetResponse getResponse = response.readEntity(PerfilGetResponse.class);
		assertEquals("el nombre del perfil creado y el obtenido deben coincidir", "ejemplo",
				getResponse.getPerfil().getUsername());
		assertEquals("el email del perfil creado y el obtenido deben coincidir", "test@test.com",
				getResponse.getPerfil().getEmail());

	}

	@Test
	public void testPerfilesPost() {
		/*
		 * PerfilPostRequest request = new PerfilPostRequest();
		 * request.setUsername("ejemplo"); request.setPassword("asd123");
		 * PerfilPostResponse response =
		 * target("/api/perfiles").request().post(Entity.json(request),
		 * PerfilPostResponse.class); assertEquals("ejemplo",
		 * response.getUsername());
		 */
		// PerfilPostResponse perfilResponse =
		// response.readEntity(PerfilPostResponse.class);

		// assertEquals("creacion exitosa de usuario - headers",
		// response.getStatus(), Response.Status.OK.getStatusCode());
		// assertEquals("creacion exitosa de usuario",
		// perfilResponse.getStatus().getCode(), 200);
	}
}
