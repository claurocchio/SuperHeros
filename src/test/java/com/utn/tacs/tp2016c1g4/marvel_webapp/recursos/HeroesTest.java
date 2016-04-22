package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

public class HeroesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(HeroesTest.class);
	}

	
	public void testHeroes() { 
		String responseMsg = target("heroes").request().get(String.class);
		assertEquals("{}", responseMsg); //mockear
	}

}
