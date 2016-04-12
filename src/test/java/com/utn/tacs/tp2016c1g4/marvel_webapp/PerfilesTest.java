package com.utn.tacs.tp2016c1g4.marvel_webapp;

import com.utn.tacs.tp2016c1g4.marvel_webapp.response.PerfilGetResponse;

public class PerfilesTest extends AbstractServerTest {

	public PerfilesTest(String testName) {
		super(testName);
	}

	public void testPerfilesGet() {
		PerfilGetResponse response = r.path("perfiles/ejemplo").get(PerfilGetResponse.class);
		assertEquals("ejemplo",response.getUsername());
	}
	
	public void testPerfilesPost() {
		
	}
}
