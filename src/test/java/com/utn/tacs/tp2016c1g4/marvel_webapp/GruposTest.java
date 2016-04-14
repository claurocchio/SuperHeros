package com.utn.tacs.tp2016c1g4.marvel_webapp;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.recursos.GrupoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.grupo.GrupoPostResponse;
import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

public class GruposTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(GrupoResource.class);
	}

	public void testCreateGrupo() {
		GrupoPostResponse grupoResponse = target("grupos").queryParam("name", "los grosos").request()
				.get(GrupoPostResponse.class);

		assertEquals(34, grupoResponse.getIdGrupo().intValue());

		GrupoPostRequest postRequest = new GrupoPostRequest("existente");
		ClientResponse response = target("grupos").request().post(Entity.json(postRequest), ClientResponse.class);
		assertEquals(202, response.getStatus());
	}

	public void testGetGrupo() {
		Grupo grupo = target("grupos/1").request().get(Grupo.class);
		assertEquals(2, grupo.getPersonajes().size());
	}

	public void testAddPersonaje() {
		GrupoPutRequest request = new GrupoPutRequest(2);
		ClientResponse response = target("grupos/1").request().put(Entity.json(request), ClientResponse.class);
		assertEquals(201, response.getStatus());
	}

	public void testDeletePersonaje() {
		ClientResponse response = target("grupos/1").request().delete(ClientResponse.class);
		assertEquals(200, response.getStatus());
	}

}
