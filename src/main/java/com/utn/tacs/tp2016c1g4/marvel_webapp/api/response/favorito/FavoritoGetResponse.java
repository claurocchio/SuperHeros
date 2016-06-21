package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class FavoritoGetResponse {

	@JsonProperty("favoritos")
	List<PersonajeMarvel> favoritos;

	public List<PersonajeMarvel> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<PersonajeMarvel> favoritos) {
		this.favoritos = favoritos;
	}
	
	public void setFavoritosPorUser(int userId,List<PersonajeMarvel> favoritosDeUser){
		this.favoritos = favoritosDeUser;
	}

}
