package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity;

import java.util.Collection;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

public class InnerUsuario{

	private Long id;
	private String userName;
	private String email;
	private Long idPerfil;
	private int lastAccess;

	public InnerUsuario(){	
	}
	
	public InnerUsuario(Usuario usuario) {
		this.id = usuario.getId();
		this.userName = usuario.getUserName();
		this.idPerfil = usuario.getIdPerfil();
		this.email = usuario.getEmail();
		this.lastAccess = usuario.getLastAccess();
	}

	public Long getId() {
		return id;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public int getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(int lastAccess) {
		this.lastAccess = lastAccess;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}