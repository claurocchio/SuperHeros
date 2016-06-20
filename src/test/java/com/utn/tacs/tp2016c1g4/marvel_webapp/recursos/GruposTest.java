package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class GruposTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testCreateGrupo() {

		GrupoPostRequest postRequest = null;
		Response response = null;

		postRequest = new GrupoPostRequest();
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos con nombre faltante debe ser bad request", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		String nombreGrupo = "unGrupo";

		postRequest.setName(nombreGrupo);
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos cuando esta vacio debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		GrupoPostResponse postResponse = response.readEntity(GrupoPostResponse.class);

		assertNotNull("nuevo grupo debe tener id asignado", postResponse.getIdGrupo());
		assertEquals("nuevo grupo debe ser identico al del request", nombreGrupo, postResponse.getNombre());

		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos con nombre existente debe ser conflict ", Status.CONFLICT.getStatusCode(),
				response.getStatus());

		postResponse = response.readEntity(GrupoPostResponse.class);

		assertNull("post a grupos en conflicto debe producir id nulo", postResponse.getIdGrupo());
		assertNull("post a grupos en conflicto debe producir nombre nulo", postResponse.getNombre());
	}

	@Test
	public void testGetGrupo() {
		Response response = null;

		response = target("/grupos/2").request().get(Response.class);
		assertEquals("get grupo cuando esta vacio debe ser not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// hago 2 post para popular grupos

		GrupoPostRequest postRequest = new GrupoPostRequest();

		postRequest.setName("grupo1");
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);

		postRequest.setName("grupo2");
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);

		// repito busqueda de grupos
		response = target("/grupos/2").request().get(Response.class);
		assertEquals("get grupo para id existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		GrupoGetResponse getResponse = response.readEntity(GrupoGetResponse.class);
		assertNotNull("get grupo response OK debe tener grupo no nulo", getResponse.getGrupo());
		assertEquals("get grupo response OK debe tener grupo con id igual al del request", 2,
				getResponse.getGrupo().getId());
		assertNotNull("get grupo response OK para grupo debe tener objeto lista de personajes",
				getResponse.getGrupo().getPersonajes());
		assertEquals("get grupo response OK para grupo nuevo no debe contener personajes", 0,
				getResponse.getGrupo().getPersonajes().size());

	}

	@Test
	public void testAddPersonaje() {

	}

}