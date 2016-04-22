package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;

@Path("personajes")
public class PersonajesResource {

	@GET
	@Produces("application/json")
	public Set<Personaje> get() {
		Set<Personaje> personajes = new HashSet<Personaje>();
		Personaje hulk = new Personaje(1, "Hulk");
		Personaje thor = new Personaje(2, "Thor");
		personajes.add(hulk);
		personajes.add(thor);
		return personajes;
	}
}
