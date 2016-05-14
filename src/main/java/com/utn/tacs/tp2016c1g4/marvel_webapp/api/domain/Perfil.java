package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

public class Perfil implements Entity {

	private Long id;
	private String userName;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
