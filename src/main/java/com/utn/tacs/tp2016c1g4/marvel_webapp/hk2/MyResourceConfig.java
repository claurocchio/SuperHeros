package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import org.glassfish.jersey.server.ResourceConfig;

public class MyResourceConfig extends ResourceConfig {

	public MyResourceConfig() {
		register(new MyApplicationBinder());
		packages(true, "com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos");
	}
}
