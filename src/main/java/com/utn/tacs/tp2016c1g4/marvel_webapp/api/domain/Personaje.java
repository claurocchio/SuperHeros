package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

public class Personaje implements Entity {

	private Long id;
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Personaje [id=" + id + ", nombre=" + nombre + "]";
	}

}
