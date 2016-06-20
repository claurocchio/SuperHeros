package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario;

public class UsuarioPostRequest {
	private String userName;
	private String pass;
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
