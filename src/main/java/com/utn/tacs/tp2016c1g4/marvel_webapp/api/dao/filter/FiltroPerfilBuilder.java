package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.HashSet;
import java.util.Set;

public class FiltroPerfilBuilder {

	private Long id;
	private String userName;

	public void clear() {
		this.id = null;
		this.userName = null;
	}

	public Set<FiltroPerfil> build() {

		Set<FiltroPerfil> filtros = new HashSet<>();

		if (this.id != null)
			filtros.add(new FiltroPerfil(FiltroPerfil.Tipo.ID, id));

		if (this.userName != null)
			filtros.add(new FiltroPerfil(FiltroPerfil.Tipo.USERNAME, userName));

		return filtros;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = Long.parseLong(id);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
