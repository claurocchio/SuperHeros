package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario;



import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class UsuarioGetResponse {

	private InnerUsuario usuario;
	private OperationStatus status;

	public InnerUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(InnerUsuario usuario) {
		this.usuario = usuario;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public static class InnerUsuario {

		private long id;
		private long idPerfil;
		private String userName;
		private String email;
		private String pass;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		public long getIdPerfil() {
			return idPerfil;
		}

		public void setIdPerfil(long idPerfil) {
			this.idPerfil = idPerfil;
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

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

	}

	public static class Builder {

		private Usuario usuario;
		private OperationStatus operationStatus;

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

				InnerUsuario innerUsuario = new InnerUsuario();
				innerUsuario.setId(usuario.getId());
				innerUsuario.setIdPerfil(usuario.getIdPerfil());
				innerUsuario.setUserName(usuario.getUserName());
				innerUsuario.setEmail(usuario.getEmail());

				response.setUsuario(innerUsuario);
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
