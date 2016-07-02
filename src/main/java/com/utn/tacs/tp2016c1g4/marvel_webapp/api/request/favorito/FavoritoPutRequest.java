package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito;

import java.util.List;

public class FavoritoPutRequest {

	private List<Long> idsPersonaje;

	public FavoritoPutRequest() {
	}

	public List<Long> getIdsPersonaje() {
		return idsPersonaje;
	}

	public void setIdsPersonaje(List<Long> idsPersonaje) {
		this.idsPersonaje = idsPersonaje;
	}
}
