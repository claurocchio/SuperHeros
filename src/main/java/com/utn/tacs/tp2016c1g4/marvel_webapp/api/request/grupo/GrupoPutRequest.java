package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

public class GrupoPutRequest {

	private Long idPersonaje;

	public GrupoPutRequest() {
	}

	public GrupoPutRequest(Long idPersonaje) {
		this.setIdPersonaje(idPersonaje);
	}

	public Long getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(Long idPersonaje) {
		this.idPersonaje = idPersonaje;
	}

}
