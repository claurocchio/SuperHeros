package com.utn.tacs.tp2016c1g4.marvel_webapp.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class PersonajeMarvelExternalFetcher implements ExternalFetcher<PersonajeMarvel> {

	private static final String URL_MARVEL_CHARACTERS = "http://gateway.marvel.com/v1/public/characters";

	private static final Logger logger = LogManager.getLogger(PersonajeMarvelExternalFetcher.class);

	@Override
	public List<PersonajeMarvel> fetch(Page page) {

		logger.debug("fetch personajes, pagina: " + page);

		List<PersonajeMarvel> personajesMarvel = new ArrayList<PersonajeMarvel>();
		URLConnection con = null;

		try {

			Properties props = new Properties();
			props.setProperty("ts", "1");
			props.setProperty("hash", "12eb73da146a932dbfe2b95253ed1fa5");
			props.setProperty("apikey", "b1b35d57fc130504f737b14e581d523b");
			props.setProperty("limit", Integer.valueOf(page.getLimit()).toString());
			props.setProperty("offset", Integer.valueOf(page.getPage() * page.getLimit()).toString());

			con = getConnection(URL_MARVEL_CHARACTERS, props);

			InputStream stream = con.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));

			StringBuffer stData = new StringBuffer();
			String stInputLine = new String();
			stream = con.getInputStream();
			in = new BufferedReader(new InputStreamReader(stream));
			while ((stInputLine = in.readLine()) != null)
				stData.append(stInputLine);

			personajesMarvel = parsearPersonajes(stData);


		} catch (Exception e) {
			logger.error("ocurrio un error al fetchear personajes de marvel: " + e.getMessage(), e);
		}

		return personajesMarvel;
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
	private ArrayList<PersonajeMarvel> parsearPersonajes(StringBuffer stData) throws IOException,
			JsonProcessingException, UnsupportedEncodingException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(stData.toString().getBytes("UTF-8"));
		JsonNode data = rootNode.get("data");
		JsonNode results = data.get("results");

		return mapper.readValue(results.toString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, PersonajeMarvel.class));
	}

	/**
	 * @param string
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private URLConnection getConnection(String host, Properties params)
			throws MalformedURLException, IOException, ProtocolException {

		String queryString = "";

		for (String key : params.stringPropertyNames()) {
			queryString += "&" + key + "=" + params.getProperty(key);
		}
		queryString.replaceFirst("&", "");

		URL obj = new URL(host + "?" + queryString);
		URLConnection con = obj.openConnection();
		con.connect();

		return con;
	}

}
