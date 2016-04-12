
package com.utn.tacs.tp2016c1g4.marvel_webapp;

import java.util.HashSet;
import java.util.Set;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.core.header.MediaTypes;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.PerfilGetResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;

public class MainTest extends AbstractServerTest {

	public MainTest(String testName) {
		super(testName);
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
