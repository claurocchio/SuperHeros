package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity;

import java.util.Map;

public class InnerPersonaje {

	private long id;
	private String nombre;
	private String descripcion;
	private Map<String, String> imagen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Map<String, String> getImagen() {
		return imagen;
	}

	public void setImagen(Map<String, String> imagen) {
		this.imagen = imagen;
	}

}