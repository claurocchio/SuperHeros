package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;

@Path("personajes")
public class PersonajesResource {

	private static final Logger logger = LogManager.getLogger(PersonajesResource.class);

	@GET
	@Produces("application/json")
	public Set<Personaje> get() {
		logger.debug("get invocado");
		Set<Personaje> personajes = new HashSet<Personaje>();
		Personaje hulk = new Personaje(1, "Hulk");
		Personaje thor = new Personaje(2, "Thor");
		personajes.add(hulk);
		personajes.add(thor);
		return personajes;
	}
}
