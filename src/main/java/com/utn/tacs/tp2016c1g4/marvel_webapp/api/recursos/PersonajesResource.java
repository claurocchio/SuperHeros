package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse;

@Path("personajes")
public class PersonajesResource {

	private static final Logger logger = LogManager.getLogger(PersonajesResource.class);
	private static final String USER_AGENT = "Mozilla/5.0";

	@GET
	@Produces("application/json")
	public Set<Personaje> get() {
		logger.debug("get invocado");
		
		String url = "http://gateway.marvel.com/v1/public/characters?ts=1&hash=12eb73da146a932dbfe2b95253ed1fa5&apikey=b1b35d57fc130504f737b14e581d523b";
		Set<Personaje> personajes = new HashSet<Personaje>();
		HttpURLConnection con;
		try {
			URL obj = new URL(url);
			Set<PersonajeGetResponse> personajesResponse = new HashSet<PersonajeGetResponse>();
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
//			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				ObjectMapper mapper = new ObjectMapper();
				personajesResponse = mapper.readValue(inputLine, new TypeReference<Set<PersonajeGetResponse>>() {
				});
				response.append(inputLine);
			}
			in.close();
			for (PersonajeGetResponse personajesResp : personajesResponse) {
				personajes.add(personajesResp.getPersonaje());
			}
		} catch (IOException e) {
			
		}
		return personajes;


		//print result
//		System.out.println(response.toString());
//		Set<Personaje> personajes = new HashSet<Personaje>();
//		Personaje hulk = new Personaje(1, "Hulk");
//		Personaje thor = new Personaje(2, "Thor");
//		personajes.add(hulk);
//		personajes.add(thor);
//		return personajes;
	}
}
