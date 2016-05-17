package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.FavoritoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;

import static org.junit.Assert.*;

public class FavoritosTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(FavoritoResource.class).register(GuiceInMemoryFeature.class);
	}

	@Test
	public void testFavoritos() {
		FavoritoGetResponse response = target("favoritos").request().get(FavoritoGetResponse.class);
		assertTrue(response.getFavoritos().size() > 0);
	}

	@Test
	public void testMarcarFavorito() {
		FavoritoPostRequest request = new FavoritoPostRequest(5);
		Response response = target("favoritos").request().post(Entity.json(request), Response.class);
		assertEquals(201, response.getStatus());
	}

	@Test
	public void testAgregarFavoritoSinRequest() {
		Response response = target("favoritos").request().post(null, Response.class);
		assertEquals(400, response.getStatus());
	}

}
