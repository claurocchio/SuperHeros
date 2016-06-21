package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.task.PersonajeImporterTask;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.ExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.PersonajeMarvelExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class ExternalImporterBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(PersonajeMarvelExternalFetcher.class).to(new TypeLiteral<ExternalFetcher<PersonajeMarvel>>() {
		}).in(Singleton.class);

		bind(PersonajeImporterTask.class).to(PersonajeImporterTask.class).in(Singleton.class);

	}

}
