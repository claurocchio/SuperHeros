package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.utn.tacs.tp2016c1g4.marvel_webapp.external.ExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;
import com.utn.tacs.tp2016c1g4.marvel_webapp.mock.EmptyPersonajeFetcher;

public class MockBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(EmptyPersonajeFetcher.class).to(new TypeLiteral<ExternalFetcher<PersonajeMarvel>>() {
		}).in(Singleton.class);
		
	}

}
