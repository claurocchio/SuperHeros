package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.login;

public class LoginPostRequest {
	private String userName;
	private String pass;

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

}
