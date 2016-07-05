package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerUsuario;

@JsonInclude(Include.NON_NULL)
public class UsuarioGetResponse {

	private OperationStatus status;
	private InnerUsuario usuario;
	private Collection<InnerUsuario> usuarios;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public InnerUsuario getUsuario(){
		return this.usuario;
	}
	
	public void setUsuario(InnerUsuario usuario){
		this.usuario = usuario;
	}

	public Collection<InnerUsuario> getUsuarios(){
		return this.usuarios;
	}
	
	public void setUsuarios(Collection<InnerUsuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public static class Builder {

		private Collection<Usuario> usuarios;
		private Usuario usuario;
		private OperationStatus operationStatus;

		public Builder setUsuarios(Set<Usuario> usuarios) {
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
				InnerUsuario user = new InnerUsuario(usuario);
				response.setUsuario(user);
			}else if(usuarios != null){
				Collection<InnerUsuario> users = new HashSet<>();
				for(Usuario u : usuarios){
					InnerUsuario unUser = new InnerUsuario(u);
					users.add(unUser);
				}
				response.setUsuarios(users);
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
