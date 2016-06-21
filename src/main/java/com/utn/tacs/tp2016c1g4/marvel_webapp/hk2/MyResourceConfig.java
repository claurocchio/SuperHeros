package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import org.glassfish.jersey.server.ResourceConfig;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.task.PersonajeImporterTask;

public class MyResourceConfig extends ResourceConfig {

	public MyResourceConfig() {
		packages(true, "com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos");
		packages(true, "com.utn.tacs.tp2016c1g4.marvel_webapp.api.task");
		register(new InMemoryDaoBinder());
		register(new ExternalImporterBinder());
	}
}
