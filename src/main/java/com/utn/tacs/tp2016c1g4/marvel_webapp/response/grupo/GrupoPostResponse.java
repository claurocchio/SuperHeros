package com.utn.tacs.tp2016c1g4.marvel_webapp.response.grupo;

import com.utn.tacs.tp2016c1g4.marvel_webapp.response.OperationStatus;

public class GrupoPostResponse {

	private Integer idGrupo;
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
