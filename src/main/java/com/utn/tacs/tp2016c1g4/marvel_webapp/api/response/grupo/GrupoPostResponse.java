package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class GrupoPostResponse {

	private String nombre;
	private Long idGrupo;
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
