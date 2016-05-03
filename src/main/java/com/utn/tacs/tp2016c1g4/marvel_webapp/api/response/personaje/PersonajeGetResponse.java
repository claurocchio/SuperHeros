package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.business.Personaje;

public class PersonajeGetResponse {
	@JsonProperty
	private Meta metadata;
	@JsonProperty
	private Personaje personaje;
	
	public Meta getMetadata() {
		return metadata;
	}
	public void setMetadata(Meta metadata) {
		this.metadata = metadata;
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
}

