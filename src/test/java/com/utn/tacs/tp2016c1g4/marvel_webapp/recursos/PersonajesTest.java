package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PersonajesResource;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

public class PersonajesTest extends TestCase {

	protected Application configure() {
		return new ResourceConfig(PersonajesResource.class);
	}

	@Test
	public void testPersonajes() {
		Set<Personaje> set = new PersonajesResource().get();
		for (Iterator<Personaje> iterator = set.iterator(); iterator.hasNext();) {
			Personaje personaje =  iterator.next();
			System.out.println(personaje);
		}
		assertEquals(false, set.isEmpty());
	}

}
