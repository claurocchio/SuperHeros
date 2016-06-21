package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

@JsonInclude(Include.NON_NULL)
public class GrupoGetResponse {

	private Collection<InnerGrupo> grupos;
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

	public Collection<InnerGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Collection<InnerGrupo> grupos) {
		this.grupos = grupos;
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
		private Collection<Grupo> grupos;
		private Map<Long, Personaje> mapPersonajes;
		private OperationStatus operationStatus;

		public Builder setMapPersonajes(Map<Long, Personaje> mapPersonajes) {
			this.mapPersonajes = mapPersonajes;
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

		public Builder setGrupos(Collection<Grupo> grupos) {
			this.grupos = grupos;
			return this;
		}

		public GrupoGetResponse build() {
			GrupoGetResponse response = new GrupoGetResponse();

			if (grupos == null) {
				grupos = new HashSet<Grupo>();
			}

			if (grupo != null)
				grupos.add(grupo);

			Collection<InnerGrupo> innerGrupos = new HashSet<>();

			for (Grupo grupo : grupos) {

				InnerGrupo innerGrupo = new InnerGrupo();
				innerGrupo.setId(grupo.getId());
				innerGrupo.setName(grupo.getNombre());

				if (mapPersonajes != null) {

					Collection<Object> personajes = new HashSet<>();

					for (Long idPersonaje : grupo.getPersonajes()) {
						if (mapPersonajes.containsKey(idPersonaje)) {
							Personaje p = mapPersonajes.get(idPersonaje);
							personajes.add(p);
						}
					}

					innerGrupo.setPersonajes(personajes);
				} else {
					innerGrupo.setPersonajes(grupo.getPersonajes());
				}

				innerGrupos.add(innerGrupo);
			}

			if (grupo != null) {
				response.setGrupo(innerGrupos.iterator().next());
			} else {
				response.setGrupos(innerGrupos);
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
