package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

public class Perfil implements Entity{

	private Long id;
	private String username;
	private String email;


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

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
