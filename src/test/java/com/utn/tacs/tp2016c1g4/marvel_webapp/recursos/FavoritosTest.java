package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.FavoritoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;

import static org.junit.Assert.*;

public class FavoritosTest extends JerseyTest {
	
	@Override
    protected Application configure() {
        return new ResourceConfig(FavoritoResource.class);
    }

	public void testFavoritos() {
		Set<?> favoritos = target("favoritos").request().get(HashSet.class);
		assertEquals(2, favoritos.size());
	}

	public void testMarcarFavorito() {
		FavoritoPostRequest request = new FavoritoPostRequest(5);
		ClientResponse response = target("favoritos").request().post(Entity.json(request),ClientResponse.class);
		assertEquals(201, response.getStatus());
	}

}
