package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

import java.util.Collection;

public class GrupoPutRequest {

	private String nombre;
	private Collection<String> nombresPersonaje;

	public Collection<String> getPersonajes() {
		return nombresPersonaje;
	}

	public void setPersonajes(Collection<String> nombresPersonaje) {
		this.nombresPersonaje = nombresPersonaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
