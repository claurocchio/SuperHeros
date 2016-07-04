package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class InnerFavorito {

	private Collection<?> personajes;

	public Collection<?> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Collection<?> personajes) {
		this.personajes = personajes;
	}
}
