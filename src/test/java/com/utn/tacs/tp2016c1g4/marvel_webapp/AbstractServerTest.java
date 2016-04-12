package com.utn.tacs.tp2016c1g4.marvel_webapp;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import junit.framework.TestCase;

public abstract class AbstractServerTest extends TestCase {

	private HttpServer httpServer;

	protected WebResource r;

	public AbstractServerTest(String testName) {
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

}
