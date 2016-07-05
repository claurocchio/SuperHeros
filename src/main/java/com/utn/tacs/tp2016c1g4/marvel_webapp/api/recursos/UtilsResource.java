package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

@Path("/utils")
public class UtilsResource {

	private static final Logger logger = LogManager.getLogger(UtilsResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Personaje, FiltroPersonaje> personajeDao;
	private Dao<Grupo, FiltroGrupo> grupoDao;
	private Dao<Usuario, FiltroUsuario> usuarioDao;

	@GET
	@Path("/clean/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CleanCollection(@PathParam("entity") String entityName) {

		List<Dao<?, ?>> daos = new ArrayList<>();

		switch (Entity.findForName(entityName)) {
		case GRUPOS:
			daos.add(grupoDao);
			break;
		case PERFILES:
			daos.add(perfilDao);
			break;
		case PERSONAJES:
			daos.add(personajeDao);
			break;
		case USUARIO:
			daos.add(usuarioDao);
			break;
		case ALL:
			daos.add(grupoDao);
			daos.add(perfilDao);
			daos.add(personajeDao);
			daos.add(usuarioDao);
			break;
		default:
			break;
		}

		if (daos.size() > 0) {
			for (Dao<?, ?> dao : daos) {
				logger.debug("limpiando coleccion usando dao: " + dao.getClass().getSimpleName());
				dao.clear();
			}
			return Response.status(Status.OK).entity(new Properties()).build();
		} else {
			logger.debug("ninguna coincidencia de entidad");
		}

		return Response.status(Status.NOT_FOUND).entity(new Properties()).build();

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
