package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

public class GrupoPostRequest {

	private String name;

	public GrupoPostRequest() {

	}

	public GrupoPostRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
