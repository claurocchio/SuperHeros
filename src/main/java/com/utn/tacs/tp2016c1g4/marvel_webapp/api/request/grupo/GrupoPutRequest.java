package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

import java.util.Collection;

public class GrupoPutRequest {

	private String nombre;
	private Collection<Long> idPersonajes;

	public Collection<Long> getIdPersonajes() {
		return idPersonajes;
	}

	public void setIdPersonajes(Collection<Long> idPersonajes) {
		this.idPersonajes = idPersonajes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
