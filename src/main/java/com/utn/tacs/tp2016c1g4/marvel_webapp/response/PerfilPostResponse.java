package com.utn.tacs.tp2016c1g4.marvel_webapp.response;

public class PerfilPostResponse {
	private OperationStatus status;

	private String username;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
