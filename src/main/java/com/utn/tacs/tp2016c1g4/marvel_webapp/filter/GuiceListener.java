package com.utn.tacs.tp2016c1g4.marvel_webapp.filter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.utn.tacs.tp2016c1g4.marvel_webapp.guise.modules.GuiceInMemoryModule;

public class GuiceListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {

			@Override
			protected void configureServlets() {

				ResourceConfig rc = new PackagesResourceConfig("com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos");
				for (Class<?> resource : rc.getClasses()) {
					bind(resource);
				}

				serve("/*").with(GuiceContainer.class);
			}

		}, new GuiceInMemoryModule());

	}

}
