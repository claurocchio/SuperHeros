package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse;

@Path("personajes")
public class PersonajesResource {

	private static final Logger logger = LogManager.getLogger(PersonajesResource.class);
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String URL_MARVEL_CHARACTERS = "http://gateway.marvel.com/v1/public/characters?ts=1&hash=12eb73da146a932dbfe2b95253ed1fa5&apikey=b1b35d57fc130504f737b14e581d523b";
	private static final String URL_PARAM_LIMIT = "&limit=";
	private static final int URL_VALUE_LIMIT = 100;
	private static final String URL_PARAM_OFFSET = "&offset=";

	private Dao<Personaje, FiltroPersonaje> personajeDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		ArrayList<Personaje> personajes = new ArrayList<Personaje>();
		URLConnection con;
		PersonajeGetResponse response = new PersonajeGetResponse();
		OperationStatus operationStatus = new OperationStatus();
		try {
			con = getConnection(URL_MARVEL_CHARACTERS + URL_PARAM_LIMIT + URL_VALUE_LIMIT);
			InputStream stream = con.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));

			for (int i = 1;; i++) {
				// try {
				StringBuffer stData = new StringBuffer();
				String stInputLine = new String();
				stream = con.getInputStream();
				in = new BufferedReader(new InputStreamReader(stream));
				while ((stInputLine = in.readLine()) != null)
					stData.append(stInputLine);

				ArrayList<Personaje> personajes_aux = parsearPersonajes(stData);

				if (personajes_aux.isEmpty())
					break;

				personajes.addAll(personajes_aux);

				String offset = Integer.toString(i * URL_VALUE_LIMIT);
				stream.close();
				in.close();
				// con.disconnect();
				try {
					con = getConnection(
							URL_MARVEL_CHARACTERS + URL_PARAM_LIMIT + URL_VALUE_LIMIT + URL_PARAM_OFFSET + offset);
				} catch (Exception e) {
					e.printStackTrace();
					operationStatus.setMessage(e.getMessage());
					response.setStatus(operationStatus);
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
				}
			}
			// System.out.println("Termine de ejecutar Get con response code :
			// ");
			System.out.println("\n Encontre : " + personajes.size() + " PERSONAJES");
			// for (Iterator<Personaje> iterator = personajes.iterator();
			// iterator.hasNext();) {
			// Personaje personaje = (Personaje) iterator.next();
			// System.out.println(personaje.getName());
			// }
			response.setPersonajes(personajes);
		} catch (Exception e) {
			e.printStackTrace();
			operationStatus.setMessage(e.getMessage());
			response.setStatus(operationStatus);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
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

	/**
	 * @param stData
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private ArrayList<Personaje> parsearPersonajes(StringBuffer stData) throws IOException, JsonProcessingException,
			UnsupportedEncodingException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(stData.toString().getBytes("UTF-8"));
		JsonNode data = rootNode.get("data");
		JsonNode results = data.get("results");
		// ArrayList<Personaje> personajes_aux = new ArrayList<Personaje>();
		return mapper.readValue(results.toString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Personaje.class));
	}

	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
	}

}
