package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class GrupoGetResponse {

	public List<Grupo> grupos;

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
