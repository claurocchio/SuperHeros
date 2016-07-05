package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPutResponse;

@Path("/favoritos")
public class FavoritoResource {

	private static final Logger logger = LogManager.getLogger(FavoritoResource.class);

	private Properties params;
	
	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Personaje, FiltroPersonaje> personajeDao;

	// get favoritos de un usuario
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("userId") Long userId) {
		logger.debug("get invocado");
		
		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();
		
		FiltroPerfil.Builder filtroPerfilBuilder = new FiltroPerfil.Builder();
		filtroPerfilBuilder.setId(userId);
		Set<FiltroPerfil> filtroPerfil = filtroPerfilBuilder.build();

		FavoritoGetResponse.Builder responseBuilder = new FavoritoGetResponse.Builder();
		responseBuilder.setPersonajeDao(personajeDao);
		
		Perfil p = perfilDao.findOne(filtroPerfil);
		
		if(p == null){
			status = Status.NOT_FOUND;
			opStatus.setMessage("no existe el perfil solicitado");
		}else{
			status = Status.OK;
			responseBuilder.setFavoritos(p.getIdsPersonajesFavoritos());
			responseBuilder.setPersonajeDao(personajeDao);
			if (with("personajes")) {
				
				responseBuilder.setExpandirPersonajes(true);

				if (params.containsKey("img-variant")) {
					String[] variants = params.getProperty("img-variant").split(",");

					for (String variant : variants)
						responseBuilder.addVarianteImagen(variant);
				}

				if (params.containsKey("img-extension")) {
					responseBuilder.setExtensionImagen(params.getProperty("img-extension"));
				}
			}
			
		}

		opStatus.setStatusCode(status);
		responseBuilder.setOperationStatus(opStatus);

		FavoritoGetResponse response = responseBuilder.build();
		
		return Response.status(status).entity(response).build();
	}

	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("userId") Long userId, FavoritoPutRequest request) {
		logger.debug("put invocado");

		if (userId == null || request.getNombresPersonaje() == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("no se proporciono un request adecuado");
			return Response.status(400).entity(status).build();
		}

		FiltroPerfil.Builder filtroBuilder = new FiltroPerfil.Builder();
		filtroBuilder.clear();
		filtroBuilder.setId(userId);
		Set<FiltroPerfil> filtros = filtroBuilder.build();

		Response.Status status = null;
		String message;
		OperationStatus opStatus = new OperationStatus();

		Perfil perfil = null;
		try {
			perfil = perfilDao.findOne(filtros);
		} catch (ManyResultsException e) {
			// no deberia ocurrir
			logger.error("se obtuvo mas de una coincidencia de usuario");
		}

		if (perfil == null) {
			status = Response.Status.NOT_FOUND;
			message = "no existe el perfil solicitado";
		} else {
			try {
				//ver que el personaje exista
				FiltroPersonaje.Builder filtroPersonajeBuilder = new FiltroPersonaje.Builder();
				
				boolean algunoNoExiste = false;
				String personajeInexistente = null;
				Collection<Long> ids = new ArrayList<Long>();
				for(String nombre : request.getNombresPersonaje()){
					filtroPersonajeBuilder.setNombre(nombre);
					Collection<FiltroPersonaje> filtrosPersonaje = filtroPersonajeBuilder.build();
					Personaje miPersonaje = personajeDao.findOne(filtrosPersonaje);
					if(miPersonaje == null) {
						algunoNoExiste = true;
						personajeInexistente = nombre;
					}else{
						ids.add(miPersonaje.getId());
					}
				}
				
				if (algunoNoExiste) {
					status = Response.Status.NOT_FOUND;
					message = "No existe el personaje " + personajeInexistente;
				} else {
					perfil.getIdsPersonajesFavoritos().clear();
					perfil.setIdsPersonajesFavoritos(ids);
					perfilDao.update(perfil);
					status = Response.Status.CREATED;
					message = "Se han modificado " + ids.size() + " de los favoritos del usuario "
							+ userId;
				}
			} catch (Exception e) {
				e.printStackTrace();
				status = Response.Status.INTERNAL_SERVER_ERROR;
				message = e.getMessage();
			}
		}

		opStatus.setStatusCode(status);
		opStatus.setMessage(message);

		FavoritoPutResponse favoritoPutResponse = new FavoritoPutResponse();
		favoritoPutResponse.setStatus(opStatus);

		return Response.status(status).entity(favoritoPutResponse).build();
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}
	
	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
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

	private boolean with(String adicionalRequerido) {
		String with = params.getProperty("with");
		return with != null ? with.matches(".*?,?" + adicionalRequerido + ",?.*?") : false;
	}
}
