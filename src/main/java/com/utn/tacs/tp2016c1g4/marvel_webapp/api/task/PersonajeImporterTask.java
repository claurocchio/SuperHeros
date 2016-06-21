package com.utn.tacs.tp2016c1g4.marvel_webapp.api.task;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.ExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class PersonajeImporterTask implements PersonajeImporter {

	private static final Logger logger = LogManager.getLogger(PersonajeImporterTask.class);

	private Dao<Personaje, FiltroPersonaje> personajeDao;
	private ExternalFetcher<PersonajeMarvel> personajeFetcher;
	private boolean finalized;
	private boolean started;

	private long pageAmount;

	private int limit;

	public PersonajeImporterTask() {
		finalized = false;
		started = false;
		limit = 10;
		pageAmount = 10;
	}

	@Override
	public void run() {
		logger.debug("importer start");
		started = true;

		Page page = new Page();
		page.setPage(0);
		page.setLimit(limit);

		List<PersonajeMarvel> personajes = personajeFetcher.fetch(page);

		while (page.getPage() < pageAmount && personajes.size() > 0) {
			for (PersonajeMarvel pMarvel : personajes) {
				Personaje p = new Personaje();
				p.setNombre(pMarvel.getName());
				p.setDescripcion(pMarvel.getDescription());
				Map<String, Object> thumbnail = (Map<String, Object>) pMarvel.getOtherProperties().get("thumbnail");

				if (thumbnail != null && thumbnail.containsKey("path")) {
					p.setImagen(thumbnail.get("path").toString());
				}

				personajeDao.save(p);
				logger.debug("personaje guardado: " + p.toString());
			}

			page.setPage(page.getPage() + 1);
			personajes = personajeFetcher.fetch(page);
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

	@Override
	public void setPageLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void setPageAmount(long amount) {
		this.pageAmount = amount;
	}

}
