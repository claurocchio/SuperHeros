package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse;

@Path("/api/personajes")
public class PersonajesResource {

	private static final Logger logger = LogManager.getLogger(PersonajesResource.class);
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String URL_MARVEL_CHARACTERS = "http://gateway.marvel.com/v1/public/characters?ts=1&hash=12eb73da146a932dbfe2b95253ed1fa5&apikey=b1b35d57fc130504f737b14e581d523b";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		ArrayList<Personaje> personajes = new ArrayList<Personaje>();
		HttpURLConnection con;
		PersonajeGetResponse response = new PersonajeGetResponse();
		try {
			URL obj = new URL(URL_MARVEL_CHARACTERS);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// int responseCode = con.getResponseCode();
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLine = in.readLine();
//			in.close();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
			JsonNode rootNode = mapper.readTree(con.getInputStream());
			con.disconnect();
			JsonNode data = rootNode.get("data");
			JsonNode results = data.get("results");
			// for (int i = 0; i < results.size(); i++) {
			System.out.println("\nPERSONAJES: "+results.toString());
			personajes = mapper.readValue(results.toString(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Personaje.class));
			response.setPersonajes(personajes);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
}
