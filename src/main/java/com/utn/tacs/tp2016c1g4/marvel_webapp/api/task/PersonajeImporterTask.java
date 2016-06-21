package com.utn.tacs.tp2016c1g4.marvel_webapp.api.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.ExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.PersonajeMarvelExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class PersonajeImporterTask implements Runnable {

	private static final Logger logger = LogManager.getLogger(PersonajeImporterTask.class);

	private Dao<Personaje, FiltroPersonaje> personajeDao;
	private ExternalFetcher<PersonajeMarvel> personajeFetcher;
	private boolean finalized;
	private boolean started;

	public PersonajeImporterTask() {
		finalized = false;
		started = false;
	}

	@Override
	public void run() {
		logger.debug("importer start");
		started = true;

		Page page = new Page();
		page.setPage(0);
		page.setLimit(5);

		List<PersonajeMarvel> personajes = personajeFetcher.fetch(page);

		for (PersonajeMarvel pMarvel : personajes) {
			Personaje p = new Personaje();
			p.setNombre(pMarvel.getName());
			personajeDao.save(p);
			logger.debug("personaje guardado: " + p.toString());
		}

		logger.debug("importer end");
		finalized = true;
	}

	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
	}

	@Inject
	public void setPersonajeFetcher(ExternalFetcher<PersonajeMarvel> personajeFetcher) {
		this.personajeFetcher = personajeFetcher;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public boolean isStarted() {
		return started;
	}

}
