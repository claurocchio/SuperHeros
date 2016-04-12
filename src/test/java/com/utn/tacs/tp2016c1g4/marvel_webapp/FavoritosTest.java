package com.utn.tacs.tp2016c1g4.marvel_webapp;

import java.util.HashSet;
import java.util.Set;

import com.sun.jersey.api.client.ClientResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;

public class FavoritosTest extends AbstractServerTest {
	
	public FavoritosTest(String testName) {
		super(testName);
	}

	public void testFavoritos() {
		@SuppressWarnings("unchecked")
		Set<Personaje> favoritos = r.path("favoritos").get(HashSet.class);
		assertEquals(2, favoritos.size());
	}
	

	public void testMarcarFavorito() {
		ClientResponse response = r.path("favoritos/add/56").accept("application/json").type("application/json")
				.put(ClientResponse.class);
		assertEquals(201, response.getStatus());
	}

	
}
