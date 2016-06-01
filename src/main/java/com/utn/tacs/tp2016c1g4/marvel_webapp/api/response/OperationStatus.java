package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response;

import javax.ws.rs.core.Response;

public class OperationStatus {
	private int code;
	private String message;

	// TODO: tiene que ser reemplazado por code
	private int success;

	public int getCode() {
		return code;
	}

	public void setStatusCode(Response.Status status) {
		this.code = status.getStatusCode();
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OperationStatus [code=" + code + ", message=" + message + "]";
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}
}
