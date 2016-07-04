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
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

@JsonInclude(Include.NON_NULL)
public class PerfilGetResponse {

	private InnerPerfil perfil;
	private OperationStatus status;

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
		private Collection<?> favoritos;
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

		public Collection<?> getFavoritos() {
			return favoritos;
		}

		public void setFavoritos(Collection<?> favoritos) {
			this.favoritos = favoritos;
		}

	}

	public static class Builder {

		private Collection<Grupo> grupos;
		private Collection<PersonajeMarvel> favoritos;
		private Perfil perfil;
		private OperationStatus operationStatus;

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

		public Builder setFavoritos(Collection<PersonajeMarvel> favoritos) {
			this.favoritos = favoritos;
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
				} else if (perfil.getIdGrupos() != null) {
					innerPerfil.setGrupos(perfil.getIdGrupos());
				} else {
					innerPerfil.setGrupos(new ArrayList<Object>());
				}

				if (favoritos != null) {
					innerPerfil.setFavoritos(favoritos);
				} else if (perfil.getIdsPersonajesFavoritos() != null) {
					innerPerfil.setFavoritos(perfil.getIdsPersonajesFavoritos());
				} else {
					innerPerfil.setFavoritos(new ArrayList<Object>());
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
