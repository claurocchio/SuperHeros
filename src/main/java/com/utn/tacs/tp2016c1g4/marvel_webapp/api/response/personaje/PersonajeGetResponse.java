package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.ArrayList;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class PersonajeGetResponse {
//	private Meta metadata;
//	@JsonProperty
	private ArrayList<PersonajeMarvel> personajes;
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}
	public void setStatus(OperationStatus status) {
		this.status = status;
	}
	public PersonajeGetResponse() {

	}
	public PersonajeGetResponse(ArrayList<PersonajeMarvel> personajes) {
		this.personajes = personajes;
	}
//	public Meta getMetadata() {
//		return metadata;
//	}
//	public void setMetadata(Meta metadata) {
//		this.metadata = metadata;
//	}
	public ArrayList<PersonajeMarvel> getPersonajes() {
		return personajes;
	}
	public void setPersonajes(ArrayList<PersonajeMarvel> personajes) {
		this.personajes = personajes;
	}
	
}

