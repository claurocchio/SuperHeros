package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito;

import java.util.List;

public class FavoritoPutRequest {

	private List<String> nombresPersonaje;

	public FavoritoPutRequest() {
	}

	public List<String> getNombresPersonaje() {
		return nombresPersonaje;
	}

	public void setNombresPersonaje(List<String> nombresPersonaje) {
		this.nombresPersonaje = nombresPersonaje;
	}
}
