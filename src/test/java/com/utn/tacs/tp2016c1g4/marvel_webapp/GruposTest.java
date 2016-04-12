package com.utn.tacs.tp2016c1g4.marvel_webapp;

import com.sun.jersey.api.client.ClientResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.grupo.GrupoPostResponse;

public class GruposTest extends AbstractServerTest {

	public GruposTest(String testName) {
		super(testName);
	}

	public void testCreateGrupo() {
		GrupoPostRequest request = new GrupoPostRequest("los grosos");
		GrupoPostResponse grupoResponse = r.path("grupos").post(GrupoPostResponse.class, request);
		assertEquals(34, grupoResponse.getIdGrupo().intValue());

		request = new GrupoPostRequest("existente");
		ClientResponse response = r.path("grupos").post(ClientResponse.class, request);
		assertEquals(202, response.getStatus());
	}

	public void testGetGrupo() {
		Grupo grupo = r.path("grupos/1").get(Grupo.class);
		assertEquals(2, grupo.getPersonajes().size());
	}

	public void testAddPersonaje() {
		GrupoPutRequest request = new GrupoPutRequest(2);
		ClientResponse response = r.path("grupos/1").put(ClientResponse.class, request);
		assertEquals(201, response.getStatus());
	}

}
