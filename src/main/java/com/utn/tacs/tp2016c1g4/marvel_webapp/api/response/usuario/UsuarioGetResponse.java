package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class UsuarioGetResponse {

	private OperationStatus status;
	private Usuario usuario;
	@JsonProperty("usuarios")
	private List<Usuario> usuarios;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios(){
		return this.usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public static class Builder {

		private List<Usuario> usuarios;
		private Usuario usuario;
		private OperationStatus operationStatus;

		public Builder setUsuarios(List<Usuario> usuarios) {
			this.usuarios = usuarios;
			return this;
		}

		public Builder setUsuario(Usuario usuario) {
			this.usuario = usuario;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public UsuarioGetResponse build() {
			UsuarioGetResponse response = new UsuarioGetResponse();

			if (usuario != null) {
				response.setUsuario(usuario);
			}else if(usuarios != null){
				response.setUsuarios(usuarios);
			}
			
			if (operationStatus == null) {
				operationStatus = new OperationStatus();
				operationStatus.setStatusCode(Status.OK);
			}

			response.setStatus(operationStatus);
			return response;
		}
	}
}
