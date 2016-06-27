package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class FavoritoPutResponse {

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
