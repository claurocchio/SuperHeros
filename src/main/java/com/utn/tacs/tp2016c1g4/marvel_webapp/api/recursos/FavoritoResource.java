package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPostResponse;


@Path("/favoritos")
public class FavoritoResource {

	private static final Logger logger = LogManager.getLogger(FavoritoResource.class);
	private static final String URL_MARVEL_CHARACTER = "http://gateway.marvel.com/v1/public/characters/";
	private static final String URL_MARVEL_PARAMS = "?ts=1&hash=12eb73da146a932dbfe2b95253ed1fa5&apikey=b1b35d57fc130504f737b14e581d523b";
	private static final String URL_PARAM_LIMIT = "&limit=";
	private static final int URL_VALUE_LIMIT = 1;

	private Dao<Perfil, FiltroPerfil> perfilDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		List<Personaje> favoritos = new ArrayList<Personaje>();
		favoritos.add(new Personaje(new Long(1), "Hulk"));
		favoritos.add(new Personaje(new Long(2), "Thor"));

		FavoritoGetResponse response = new FavoritoGetResponse();
		response.setFavoritos(favoritos);

		return Response.status(200).entity(response).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(FavoritoPostRequest request) {
		logger.debug("post invocado");

		if (request == null || request.getIdPersonaje() == null) {
			OperationStatus status = new OperationStatus();
			status.setSuccess(0);
			status.setMessage("no se especifico id personaje o request alguno");
			logger.debug(status);
			return Response.status(400).entity(status).build();
		}

		OperationStatus status = new OperationStatus();
		status.setSuccess(1);
		status.setMessage("El personaje " + request.getIdPersonaje() + " se añadió a favoritos");

		FavoritoPostResponse response = new FavoritoPostResponse();
		response.setStatus(status);

		return Response.status(201).entity(response).build();
	}

	// get favoritos de un usuario
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("userId") Integer userId) {
		logger.debug("get invocado");

		List<Personaje> favoritosDeUser = new ArrayList<Personaje>();
		favoritosDeUser.add(new Personaje(new Long(1), "Hulk"));
		favoritosDeUser.add(new Personaje(new Long(2), "Thor"));

		FavoritoGetResponse response = new FavoritoGetResponse();
		response.setFavoritosPorUser(userId, favoritosDeUser);

		return Response.status(200).entity(response).build();
	}

	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("userId") Integer userId, FavoritoPutRequest request) {
		logger.debug("put invocado");

		if (userId == null || request.getIdPersonaje() == null) {
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
				URL obj = new URL(URL_MARVEL_CHARACTER + request.getIdPersonaje() + URL_MARVEL_PARAMS + URL_PARAM_LIMIT
						+ URL_VALUE_LIMIT);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				if (con.getResponseCode() == 404) {
					status = Response.Status.NOT_FOUND;
					message = "no existe el personaje solicitado";
				} else {
					perfil.addIdPersonajeFavorito(request.getIdPersonaje());
					perfilDao.save(perfil);
					status = Response.Status.CREATED;
					message = "El personaje " + request.getIdPersonaje() + " se añadió a los favoritos del usuario id "
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

		FavoritoPostResponse favoritoPostResponse = new FavoritoPostResponse();
		favoritoPostResponse.setStatus(opStatus);

		return Response.status(status).entity(favoritoPostResponse).build();
	}

	/**
	 * @param string
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private URLConnection getConnection(String string) throws MalformedURLException, IOException, ProtocolException {
		// HttpURLConnection con;
		URL obj = new URL(string);
		URLConnection con = obj.openConnection();
		// con = (HttpURLConnection) obj.openConnection();
		// con.setRequestMethod("GET");
		// con.setRequestProperty("User-Agent", USER_AGENT);
		con.connect();
		return con;
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}

}
