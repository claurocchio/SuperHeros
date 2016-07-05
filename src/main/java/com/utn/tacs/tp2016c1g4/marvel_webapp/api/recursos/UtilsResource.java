package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.task.PersonajeImporter;

@Path("/utils")
public class UtilsResource {

	private static final Logger logger = LogManager.getLogger(UtilsResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Personaje, FiltroPersonaje> personajeDao;
	private Dao<Grupo, FiltroGrupo> grupoDao;
	private Dao<Usuario, FiltroUsuario> usuarioDao;

	private PersonajeImporter importerTask;

	private Properties params;

	@GET
	@Path("/clean/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CleanCollection(@PathParam("entity") String entityName) {

		List<Dao<?, ?>> daos = new ArrayList<>();

		boolean resetImporter = false;

		switch (Entity.findForName(entityName)) {
		case GRUPOS:
			daos.add(grupoDao);
			break;
		case PERFILES:
			daos.add(perfilDao);
			break;
		case PERSONAJES:
			daos.add(personajeDao);
			resetImporter = true;
			break;
		case USUARIO:
			daos.add(usuarioDao);
			break;
		case ALL:
			daos.add(grupoDao);
			daos.add(perfilDao);
			daos.add(personajeDao);
			daos.add(usuarioDao);
			resetImporter = true;
			break;
		default:
			break;
		}

		Status status = null;
		OperationStatus opStatus = new OperationStatus();

		if (daos.size() > 0) {
			for (Dao<?, ?> dao : daos) {
				logger.debug("limpiando coleccion usando dao: " + dao.getClass().getSimpleName());
				dao.clear();
			}

			if (resetImporter) {
				logger.debug("reseteando importer");
				importerTask.reset();
			}

			status = Status.OK;
		} else {
			status = Status.NOT_FOUND;
			logger.debug("ninguna coincidencia de entidad");
		}

		opStatus.setStatusCode(status);
		return Response.status(status).entity(opStatus).build();

	}

	@GET
	@Path("/init/personajes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarPersonajes() {

		Map<String, Object> response = new HashMap<>();
		Status status = null;
		OperationStatus opStatus = new OperationStatus();
		response.put("status", opStatus);

		if (!importerTask.isStarted()) {
			logger.debug("invocando task");

			String value = null;
			if ((value = params.getProperty("pageLimit")) != null && !value.isEmpty() && value.matches("^\\d+$")) {
				importerTask.setPageLimit(Integer.parseInt(value));
			}

			if ((value = params.getProperty("pageAmount")) != null && !value.isEmpty() && value.matches("^\\d+$")) {
				importerTask.setPageAmount(Long.parseLong(value));
			}

			Thread t = new Thread(importerTask);
			t.start();
			opStatus.setMessage("carga de personajes iniciada correctamente");
			status = Status.OK;
		} else if (importerTask.isFinalized()) {
			opStatus.setMessage("carga de personajes finalizada previamente");
			status = Status.OK;
		} else {
			opStatus.setMessage("carga en proceso");
			status = Status.CONFLICT;
		}

		opStatus.setStatusCode(status);
		return Response.status(status).entity(response).build();
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}

	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
	}

	@Inject
	public void setGrupoDao(Dao<Grupo, FiltroGrupo> grupoDao) {
		this.grupoDao = grupoDao;
	}

	@Inject
	public void setUsuarioDao(Dao<Usuario, FiltroUsuario> usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Inject
	public void setImporterTask(PersonajeImporter importerTask) {
		this.importerTask = importerTask;
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		logger.debug("catcheando uri info en perfiles");

		this.params = new Properties();
		for (String key : uriInfo.getQueryParameters().keySet()) {
			this.params.setProperty(key, uriInfo.getQueryParameters().getFirst(key));
			logger.debug("query param[ " + key + " = " + this.params.getProperty(key) + " ]");
		}
	}

	private enum Entity {
		GRUPOS("grupos"), PERFILES("perfiles"), PERSONAJES("personajes"), USUARIO("usuarios"), ALL("all");

		private String nombre;

		Entity(String nombre) {
			this.nombre = nombre;
		}

		public static Entity findForName(String nombre) {
			for (Entity e : Entity.values()) {
				if (e.nombre.equals(nombre))
					return e;
			}
			return null;
		}
	}
}
