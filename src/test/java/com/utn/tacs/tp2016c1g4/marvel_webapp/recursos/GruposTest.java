package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.GrupoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class GruposTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(GrupoResource.class).register(GuiceInMemoryFeature.class);
	}

	@Test
	public void testCreateGrupo() {

		GrupoPostRequest postRequest = null;
		Response response = null;

		postRequest = new GrupoPostRequest();
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		postRequest.setName("unGrupo");
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		// reiterar insercion de grupo
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetGrupo() {
		Response response = target("/api/grupos/2").request().get(Response.class);
		GrupoGetResponse grupoResponse = response.readEntity(GrupoGetResponse.class);
		// assertEquals(200, response.getStatus());
		// assertEquals(new Long(2), grupoResponse.getGrupos().get(0).getId());
		// Response response = target("grupos/2").request().get(Response.class);
		// GrupoGetResponse grupoResponse =
		// response.readEntity(GrupoGetResponse.class);
		assertEquals(404, response.getStatus());
		// assertEquals(new Long(2), grupoResponse.getGrupos().get(0).getId());
	}

	@Test
	public void testAddPersonaje() {
		GrupoPutRequest request = new GrupoPutRequest(new Long(2));
		Response response = target("/api/grupos/1").request().put(Entity.json(request), Response.class);
		assertEquals(404, response.getStatus());
		// GrupoPutRequest request = new GrupoPutRequest();
		// request.setIdPersonaje(new Long(2));
		// Response response =
		// target("grupos/1").request().put(Entity.json(request),
		// Response.class);
		// assertEquals(404, response.getStatus());
	}

}
