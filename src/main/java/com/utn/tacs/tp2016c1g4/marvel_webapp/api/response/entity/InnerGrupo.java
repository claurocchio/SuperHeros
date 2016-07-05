package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class InnerGrupo {

	private long id;
	private String name;
	private Collection<?> personajes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<?> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Collection<?> personajes) {
		this.personajes = personajes;
	}
}
