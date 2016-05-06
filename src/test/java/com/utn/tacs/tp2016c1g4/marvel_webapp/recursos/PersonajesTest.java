package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PersonajesResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse;

import junit.framework.TestCase;

public class PersonajesTest extends JerseyTest {

	protected Application configure() {
		return new ResourceConfig(PersonajesResource.class);
	}

	@Test
	public void testPersonajes() {
		PersonajeGetResponse response = target("personajes").request().get(PersonajeGetResponse.class);
		assertTrue(response.getPersonajes().size() > 0);
//		for (Iterator<Personaje> iterator = personajes.iterator(); iterator.hasNext();) {
//			Personaje personaje =  iterator.next();
//			System.out.println(personaje);
//		}
//		assertEquals(false, personajes.isEmpty());
	}

}
