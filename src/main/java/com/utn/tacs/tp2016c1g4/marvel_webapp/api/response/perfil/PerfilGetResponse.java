package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil;

import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

public class PerfilGetResponse extends Perfil{

	private List<Grupo> grupos;

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
