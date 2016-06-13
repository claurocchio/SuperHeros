package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.GrupoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

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
		assertEquals("post a grupos con nombre faltante debe ser bad request", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		String nombreGrupo = "unGrupo";

		postRequest.setName(nombreGrupo);
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos cuando esta vacio debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		GrupoPostResponse postResponse = response.readEntity(GrupoPostResponse.class);

		assertNotNull("nuevo grupo debe tener id asignado", postResponse.getIdGrupo());
		assertEquals("nuevo grupo debe ser identico al del request", nombreGrupo, postResponse.getNombre());

		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos con nombre existente debe ser conflict ", Status.CONFLICT.getStatusCode(),
				response.getStatus());

		postResponse = response.readEntity(GrupoPostResponse.class);

		assertNull("post a grupos en conflicto debe producir id nulo", postResponse.getIdGrupo());
		assertNull("post a grupos en conflicto debe producir nombre nulo", postResponse.getNombre());
	}

	@Test
	public void testGetGrupo() {
		Response response = null;

		response = target("/api/grupos/2").request().get(Response.class);
		assertEquals("get grupo cuando esta vacio debe ser not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// hago 2 post para popular grupos

		GrupoPostRequest postRequest = new GrupoPostRequest();

		postRequest.setName("grupo1");
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);

		postRequest.setName("grupo2");
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);

		// repito busqueda de grupos
		response = target("/api/grupos/2").request().get(Response.class);
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
	public void testPutPersonajesEnGrupo() {
		Response response = null;

		GrupoPutRequest putRequest = new GrupoPutRequest();
		GrupoGetResponse getResponse = null;
		GrupoPostRequest postRequest = null;

		response = target("/api/grupos/1").request().put(Entity.json(putRequest), Response.class);

		assertEquals("no proveer ninguna información en el put request arroja bad request",
				Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		// se crea el grupo

		postRequest = new GrupoPostRequest();

		postRequest.setName("grupo1");
		response = target("/api/grupos").request().post(Entity.json(postRequest), Response.class);

		assertEquals("grupo creado status ok previo al put", Status.OK.getStatusCode(), response.getStatus());

		// reintentando put

		putRequest.setIdPersonajes(new ArrayList<Long>());
		response = target("/api/grupos/2").request().put(Entity.json(putRequest), Response.class);

		assertEquals("put a grupo no existente debe ser not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		response = target("/api/grupos/1").request().put(Entity.json(putRequest), Response.class);

		assertEquals("put sobre grupo existente con parametros correctos es OK", Status.OK.getStatusCode(),
				response.getStatus());

		// get para analizar las modificaciones sobre el grupo

		response = target("/api/grupos/1").request().get(Response.class);
		assertEquals("grupo creado previamente debe ser OK", Status.OK.getStatusCode(), response.getStatus());
		getResponse = response.readEntity(GrupoGetResponse.class);
		assertEquals("grupo modificado con coleccion vacia debe tener 0 personajes", 0,
				getResponse.getGrupo().getPersonajes().size());

		// edicion de nombre de grupo

		putRequest.setIdPersonajes(null);
		putRequest.setNombre("otronombre");
		response = target("/api/grupos/1").request().put(Entity.json(putRequest), Response.class);
		response = target("/api/grupos/1").request().get(Response.class);
		getResponse = response.readEntity(GrupoGetResponse.class);

		assertEquals("nombre de grupo debe haber cambiado tras put con nombre", "otronombre",
				getResponse.getGrupo().getName());

		// edicion de personajes del grupo

		List<Long> idPersonajes = new ArrayList<Long>();
		idPersonajes.add(1L);
		idPersonajes.add(2L);

		putRequest.setIdPersonajes(idPersonajes);
		putRequest.setNombre(null);

		response = target("/api/grupos/1").request().put(Entity.json(putRequest), Response.class);

		assertEquals("put sobre grupo existente con parametros correctos es OK", Status.OK.getStatusCode(),
				response.getStatus());

		// get para analizar las modificaciones sobre el grupo luego de
		// modificar personajes

		response = target("/api/grupos/1").request().get(Response.class);
		assertEquals("get sobre grupo creado previamente debe ser OK", Status.OK.getStatusCode(), response.getStatus());
		getResponse = response.readEntity(GrupoGetResponse.class);
		assertEquals("grupo modificado con personajes debe tener la misma cantidad", 2,
				getResponse.getGrupo().getPersonajes().size());
		assertEquals("grupo modificado debe mantener cambios previos sobre el nombre", "otronombre",
				getResponse.getGrupo().getName());

	}

}
