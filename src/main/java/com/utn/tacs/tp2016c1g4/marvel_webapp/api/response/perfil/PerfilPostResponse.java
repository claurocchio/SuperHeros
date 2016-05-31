package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class PerfilPostResponse extends Perfil{
	private OperationStatus status;


	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

}
