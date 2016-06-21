package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class GrupoGetResponse {

	private InnerGrupo grupo;
	private OperationStatus status;

	public InnerGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(InnerGrupo grupo) {
		this.grupo = grupo;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public static class InnerGrupo {

		private long id;
		private String name;
		private Collection<?> personajes;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Collection<?> getPersonajes() {
			return personajes;
		}

		public void setPersonajes(Collection<?> personajes) {
			this.personajes = personajes;
		}
	}

	public static class Builder {

		private Grupo grupo;
		private Collection<Personaje> personajes;
		private OperationStatus operationStatus;

		public Builder setPersonajes(Collection<Personaje> personajes) {
			this.personajes = personajes;
			return this;
		}

		public Builder setGrupo(Grupo grupo) {
			this.grupo = grupo;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public GrupoGetResponse build() {
			GrupoGetResponse response = new GrupoGetResponse();

			InnerGrupo innerGrupo = new InnerGrupo();

			if (grupo != null) {
				innerGrupo.setId(grupo.getId());
				innerGrupo.setName(grupo.getNombre());

				if (personajes != null) {
					innerGrupo.setPersonajes(personajes);
				} else {
					innerGrupo.setPersonajes(grupo.getPersonajes());
				}

				response.setGrupo(innerGrupo);
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
