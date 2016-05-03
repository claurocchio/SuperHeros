package com.utn.tacs.tp2016c1g4.marvel_webapp.guisemodules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Container;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.impl.HeroeMemoryContainer;

public class GuiceMainModule extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(

				new ServletModule() {
					@Override
					protected void configureServlets() {

						bind(new TypeLiteral<Container<Personaje>>() {
						}).to(HeroeMemoryContainer.class);

						ResourceConfig rc = new PackagesResourceConfig("com.utn.tacs");

						for (Class<?> resource : rc.getClasses()) {
							bind(resource);
						}

						serve("/*").with(GuiceContainer.class);
					}
				}

		);
	}
}
