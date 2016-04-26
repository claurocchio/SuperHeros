package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response;

public class OperationStatus {
	private int success;
	private String message;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OperationStatus [success=" + success + ", message=" + message + "]";
	}

}
