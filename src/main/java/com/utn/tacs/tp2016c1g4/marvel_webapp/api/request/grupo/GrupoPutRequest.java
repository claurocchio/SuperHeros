package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

public class GrupoPutRequest {

	private Integer idPersonaje;

	public GrupoPutRequest(Integer idPersonaje) {
		this.setIdPersonaje(idPersonaje);
	}

	public Integer getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(Integer idPersonaje) {
		this.idPersonaje = idPersonaje;
	}

}
