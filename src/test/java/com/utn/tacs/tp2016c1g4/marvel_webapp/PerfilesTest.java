package com.utn.tacs.tp2016c1g4.marvel_webapp;

import com.utn.tacs.tp2016c1g4.marvel_webapp.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.response.perfil.PerfilPostResponse;

public class PerfilesTest extends AbstractServerTest {

	public PerfilesTest(String testName) {
		super(testName);
	}

	public void testPerfilesGet() {
		PerfilGetResponse response = r.path("perfiles/ejemplo").get(PerfilGetResponse.class);
		assertEquals("ejemplo",response.getUsername());
	}
	
	public void testPerfilesPost() {
		PerfilPostRequest request = new PerfilPostRequest();
		request.setUsername("ejemplo");
		request.setPassword("asd123");
		PerfilPostResponse response = r.path("perfiles").post(PerfilPostResponse.class,request);
		assertEquals("ejemplo",response.getUsername());
	}
}
