package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import org.glassfish.jersey.server.ResourceConfig;

public class MyTestResourceConfig extends ResourceConfig {

	public MyTestResourceConfig() {
		packages(true, "com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos");
		packages(true, "com.utn.tacs.tp2016c1g4.marvel_webapp.api.task");
		register(new InMemoryDaoBinder());
		register(new MockBinder());
	}

}
