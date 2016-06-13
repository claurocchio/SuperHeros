package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

import java.util.Collection;

public class Grupo implements Entity {
	private Long id;
	private String nombre;
	private Collection<Long> personajes;

	public Grupo() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Long> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Collection<Long> personajes) {
		this.personajes = personajes;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
