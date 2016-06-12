package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.*;

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

		Response response = null;

		PerfilPostRequest postRequest = new PerfilPostRequest();
		postRequest.setUsername("ejemplo");
		response = target("/api/perfiles").request().post(Entity.json(postRequest), Response.class);
		assertEquals("bad request al no proveer contraseña al post de perfil", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		postRequest.setPassword("123");
		response = target("/api/perfiles").request().post(Entity.json(postRequest), Response.class);
		assertEquals("bad request al no proveer email al post de perfil", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		postRequest.setEmail("test@test.com");
		response = target("/api/perfiles").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("/api/perfiles/ejemplo").request().get(Response.class);

		PerfilGetResponse perfilGet = response.readEntity(PerfilGetResponse.class);
		assertNotNull("perfil recien cargado no puede ser null", perfilGet.getPerfil());
		assertEquals("perfiles nuevos no deben tener favoritos asignados", 0,
				perfilGet.getPerfil().getFavoritos().size());
		assertEquals("perfiles nuevos no deben tener grupos asignados", 0, perfilGet.getPerfil().getGrupos().size());

		response = target("/api/perfiles").request().post(Entity.json(postRequest), Response.class);
		assertEquals("perfiles con el mismo username tienen que ser conflict", Status.CONFLICT.getStatusCode(),
				response.getStatus());

	}
}
