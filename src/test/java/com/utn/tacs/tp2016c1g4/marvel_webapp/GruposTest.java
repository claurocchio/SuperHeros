package com.utn.tacs.tp2016c1g4.marvel_webapp;

import com.utn.tacs.tp2016c1g4.marvel_webapp.business.Grupo;

public class GruposTest extends AbstractServerTest {

	public GruposTest(String testName) {
		super(testName);
	}
	

	public void testGrupos() {
		Grupo grupo = r.path("grupos/1").accept("application/json").type("application/json").get(Grupo.class);
		assertEquals(2, grupo.getPersonajes().size());
	}

}
