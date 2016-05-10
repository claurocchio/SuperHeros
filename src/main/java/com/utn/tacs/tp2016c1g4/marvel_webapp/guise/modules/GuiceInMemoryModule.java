package com.utn.tacs.tp2016c1g4.marvel_webapp.guise.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Repository;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.impl.InMemoryRepository;

public class GuiceInMemoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<Repository<Personaje>>() {
		}).to(new TypeLiteral<InMemoryRepository<Personaje>>() {
		});
	}
}
