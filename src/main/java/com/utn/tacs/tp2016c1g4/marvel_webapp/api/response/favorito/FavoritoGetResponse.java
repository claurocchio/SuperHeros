package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;

public class FavoritoGetResponse {

	@JsonProperty("favoritos")
	List<Personaje> favoritos;

	public List<Personaje> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Personaje> favoritos) {
		this.favoritos = favoritos;
	}

}
