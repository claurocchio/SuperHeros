package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class PerfilGetResponse extends Perfil {

	private InnerPerfil perfil;
	private OperationStatus status;
	private List<Grupo> grupos;

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public InnerPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(InnerPerfil perfil) {
		this.perfil = perfil;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public static class InnerPerfil {

		private long id;
		private String username;
		private String email;

		private Collection<?> grupos;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Collection<?> getGrupos() {
			return grupos;
		}

		public void setGrupos(Collection<?> grupos) {
			this.grupos = grupos;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

	public static class Builder {

		private Collection<Long> idGrupos;
		private Collection<Grupo> grupos;
		private Perfil perfil;
		private OperationStatus operationStatus;

		public Builder setIdGrupos(Collection<Long> idGrupos) {
			this.idGrupos = idGrupos;
			return this;
		}

		public Builder setGrupos(Collection<Grupo> grupos) {
			this.grupos = grupos;
			return this;
		}

		public Builder setPerfil(Perfil perfil) {
			this.perfil = perfil;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public PerfilGetResponse build() {
			PerfilGetResponse response = new PerfilGetResponse();

			if (perfil != null) {

				InnerPerfil innerPerfil = new InnerPerfil();
				innerPerfil.setId(perfil.getId());
				innerPerfil.setUsername(perfil.getUsername());
				innerPerfil.setEmail(perfil.getEmail());
				
				if (grupos != null) {
					innerPerfil.setGrupos(grupos);
				} else if (this.idGrupos != null) {
					innerPerfil.setGrupos(idGrupos);
				} else {
					innerPerfil.setGrupos(new ArrayList<Object>());
				}

				response.setPerfil(innerPerfil);
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
