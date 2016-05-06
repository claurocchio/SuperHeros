package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

public class Perfil implements Entity{

	private Long id;
	private String username;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
