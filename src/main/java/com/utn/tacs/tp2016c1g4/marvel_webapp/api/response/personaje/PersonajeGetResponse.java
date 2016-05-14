package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.ArrayList;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class PersonajeGetResponse {
//	private Meta metadata;
//	@JsonProperty
	private ArrayList<Personaje> personajes;
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}
	public void setStatus(OperationStatus status) {
		this.status = status;
	}
	public PersonajeGetResponse() {

	}
	public PersonajeGetResponse(ArrayList<Personaje> personajes) {
		this.personajes = personajes;
	}
//	public Meta getMetadata() {
//		return metadata;
//	}
//	public void setMetadata(Meta metadata) {
//		this.metadata = metadata;
//	}
	public ArrayList<Personaje> getPersonajes() {
		return personajes;
	}
	public void setPersonajes(ArrayList<Personaje> personajes) {
		this.personajes = personajes;
	}
	
}

