package com.utn.tacs.tp2016c1g4.marvel_webapp.api.business;

import java.util.HashSet;
import java.util.Set;

public class Grupo {

	private int id;
	private String nombre;
	private Set<Personaje> personajes;

	public Grupo(String nombre) {
		super();
		this.nombre = nombre;
		this.personajes = new HashSet<Personaje>();
	}

	public Grupo(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.personajes = new HashSet<Personaje>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
