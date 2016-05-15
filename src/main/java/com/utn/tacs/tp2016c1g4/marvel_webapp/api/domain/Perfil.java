package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

import java.util.Collection;

public class Perfil implements Entity {

	private Long id;
	private String userName;
	private Collection<Long> idGrupos;
	private Collection<Long> idsPersonajesFavoritos;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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
