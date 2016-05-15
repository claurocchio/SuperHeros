package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class GrupoDeleteResponse {
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

}
