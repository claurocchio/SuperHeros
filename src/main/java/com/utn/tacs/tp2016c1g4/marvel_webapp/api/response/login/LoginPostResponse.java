package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.login;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class LoginPostResponse {

	private OperationStatus status;
	private String token;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
