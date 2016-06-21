package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

public class PersonajeGetResponse {

	private Collection<InnerPersonaje> personajes;
	private OperationStatus status;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Collection<InnerPersonaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Collection<InnerPersonaje> personajes) {
		this.personajes = personajes;
	}

	public static class InnerPersonaje {

		private long id;
		private String nombre;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

	}

	public static class Builder {

		private Collection<Personaje> personajes;
		private OperationStatus status;

		public Builder setPersonajes(Collection<Personaje> personajes) {
			this.personajes = personajes;
			return this;
		}

		public Builder setOperationstatus(OperationStatus opStatus) {
			this.status = opStatus;
			return this;
		}

		public PersonajeGetResponse build() {
			PersonajeGetResponse response = new PersonajeGetResponse();

			if (personajes == null) {
				personajes = new ArrayList<Personaje>();
			}

			List<InnerPersonaje> innerPersonajes = new ArrayList<InnerPersonaje>();

			for (Personaje p : personajes) {
				InnerPersonaje innerPersonaje = new InnerPersonaje();
				innerPersonaje.setId(p.getId());
				innerPersonaje.setNombre(p.getNombre());

				innerPersonajes.add(innerPersonaje);
			}

			response.setPersonajes(innerPersonajes);

			if (status == null) {
				status = new OperationStatus();
				status.setStatusCode(Status.OK);
			}

			response.setStatus(status);

			return response;
		}
	}

}
