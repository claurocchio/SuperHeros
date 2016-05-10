package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

import java.util.Set;

public class Grupo implements Entity {
	private Long id;
	private String nombre;
	private Set<Personaje> personajes;

	public Grupo() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Set<Personaje> personajes) {
		this.personajes = personajes;
	}

	public void addPersonaje(Personaje personaje) {
		this.personajes.add(personaje);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
