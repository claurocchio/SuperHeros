
package com.utn.tacs.tp2016c1g4.marvel_webapp;

import com.sun.jersey.core.header.MediaTypes;

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
