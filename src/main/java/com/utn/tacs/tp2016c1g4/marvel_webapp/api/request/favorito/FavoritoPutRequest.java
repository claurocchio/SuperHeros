package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito;

public class FavoritoPutRequest {

	private Long idPersonaje;

	public FavoritoPutRequest() {
	}

	public FavoritoPutRequest(Long idPersonaje) {
		this.setIdPersonaje(idPersonaje);
	}

	public Long getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(Long idPersonaje) {
		this.idPersonaje = idPersonaje;
	}
}
