package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.task.PersonajeImporter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.task.PersonajeImporterTask;

@Path("/personajes")
public class PersonajesResource {

	private static final Logger logger = LogManager.getLogger(PersonajesResource.class);

	private Dao<Personaje, FiltroPersonaje> personajeDao;
	private PersonajeImporter importerTask;
	private Properties params;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		if (!importerTask.isStarted()) {
			logger.debug("invocando task");
			Thread t = new Thread(importerTask);
			t.start();
		}

		Page page = new Page();
		page.setPage(0);
		page.setLimit(5);

		if (params.containsKey("page")) {
			page.setPage(Integer.parseInt(params.getProperty("page")));
		}

		if (params.containsKey("limit")) {
			page.setLimit(Integer.parseInt(params.getProperty("limit")));
		}

		FiltroPersonaje.Builder filtroBuilder = new FiltroPersonaje.Builder();

		if (params.containsKey("id"))
			filtroBuilder.setId(Long.parseLong(params.getProperty("id").toString()));

		if (params.containsKey("nombre"))
			filtroBuilder.setNombre(params.getProperty("nombre").toString());

		Collection<FiltroPersonaje> filters = filtroBuilder.build();

		Set<Personaje> personajes = personajeDao.find(filters, page);

		Status status = Status.OK;
		OperationStatus opStatus = new OperationStatus();
		opStatus.setStatusCode(status);
		opStatus.setMessage("");

		PersonajeGetResponse.Builder responseBuilder = new PersonajeGetResponse.Builder();
		responseBuilder.setPersonajes(personajes);
		responseBuilder.setOperationstatus(opStatus);
		responseBuilder.setPage(page);

		if (params.containsKey("img-variant")) {
			String[] variants = params.getProperty("img-variant").split(",");

			for (String variant : variants)
				responseBuilder.addVarianteImagen(variant);
		}

		if (params.containsKey("img-extension")) {
			responseBuilder.setExtensionImagen(params.getProperty("img-extension"));
		}

		PersonajeGetResponse response = responseBuilder.build();

		return Response.status(status).entity(response).build();
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		logger.debug("catcheando uri info en personajes");

		this.params = new Properties();
		for (String key : uriInfo.getQueryParameters().keySet()) {
			this.params.setProperty(key, uriInfo.getQueryParameters().getFirst(key));
			logger.debug("query param[ " + key + " = " + this.params.getProperty(key) + " ]");
		}
	}

	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
	}

	@Inject
	public void setImporterTask(PersonajeImporterTask importerTask) {
		this.importerTask = importerTask;
	}

}
