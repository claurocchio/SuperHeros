package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Perfil implements Entity {

	@JsonProperty("_id")
	private Long id;
	private String username;
	private String email;

	private Collection<Long> idGrupos;
	private Collection<Long> idsPersonajesFavoritos = new ArrayList<Long>();

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Long> getIdGrupos() {
		return idGrupos;
	}

	public void setIdGrupos(Collection<Long> idGrupos) {
		this.idGrupos = idGrupos;
	}

	public Collection<Long> getIdsPersonajesFavoritos() {
		return idsPersonajesFavoritos;
	}

	public void setIdsPersonajesFavoritos(Collection<Long> idsPersonajesFavoritos) {
		this.idsPersonajesFavoritos = idsPersonajesFavoritos;
	}

	public void addIdPersonajeFavorito(Long idPersonajeFavorito) {
		this.idsPersonajesFavoritos.add(idPersonajeFavorito);
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", username=" + username + ", email=" + email + "]";
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
