
package com.utn.tacs.tp2016c1g4.marvel_webapp;

import java.util.HashSet;
import java.util.Set;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.core.header.MediaTypes;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;

public class MainTest extends TestCase {

	private HttpServer httpServer;

	private WebResource r;

	public MainTest(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// start the Grizzly2 web container
		httpServer = Main.startServer();

		// create the client
		Client c = Client.create();
		r = c.resource(Main.BASE_URI);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		httpServer.stop();
	}

	public void testFavoritos() {
		@SuppressWarnings("unchecked")
		Set<Personaje> favoritos = r.path("favoritos").get(HashSet.class);
		assertEquals(2, favoritos.size());
	}

	public void testGrupos() {
		Grupo grupo = r.path("grupos/1").accept("application/json").type("application/json").get(Grupo.class);
		assertEquals(2, grupo.getPersonajes().size());
	}

	public void testHeroes() {
		String responseMsg = r.path("heroes").get(String.class);
		assertEquals("{}", responseMsg);
	}

	public void testPerfiles() {
		String responseMsg = r.path("perfiles").get(String.class);
		assertEquals("{}", responseMsg);
	}

	public void testMarcarFavorito() {
		ClientResponse response = r.path("favoritos/add/56").accept("application/json").type("application/json")
				.put(ClientResponse.class);
		assertEquals(201, response.getStatus());
	}

	/**
	 * Test if a WADL document is available at the relative path
	 * "application.wadl".
	 */
	public void testApplicationWadl() {
		String serviceWadl = r.path("application.wadl").accept(MediaTypes.WADL).get(String.class);

		assertTrue(serviceWadl.length() > 0);
	}
}
