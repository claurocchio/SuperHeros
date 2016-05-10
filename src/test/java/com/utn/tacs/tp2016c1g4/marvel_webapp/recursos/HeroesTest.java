package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.utn.tacs.tp2016c1g4.marvel_webapp.GuiceInMemoryFeature;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.HeroeResource;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class HeroesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(HeroeResource.class).register(GuiceInMemoryFeature.class);
	}

	@Test
	public void testHeroes() {
		Response response = target("heroes").request().get(Response.class);
		assertEquals(200, response.getStatus());
	}

}
