package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil;

import java.util.ArrayList;
import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class PerfilGetResponse {

	private long id;
	private String username;
	private List<Grupo> grupos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
