package com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo;

public class GrupoPostRequest {

	private String name;
	private Long idUsuario;

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public GrupoPostRequest() {

	}

	public GrupoPostRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
