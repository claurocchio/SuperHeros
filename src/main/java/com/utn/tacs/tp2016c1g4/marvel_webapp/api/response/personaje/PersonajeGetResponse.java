package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		private String descripcion;
		private Map<String, String> imagen;

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

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Map<String, String> getImagen() {
			return imagen;
		}

		public void setImagen(Map<String, String> imagen) {
			this.imagen = imagen;
		}

	}

	public static class Builder {

		private static final String DEFAULT_EXTENSION = "jpg";
		private static final String DEFAULT_VARIANT = "standard_large";

		private Collection<Personaje> personajes;
		private Collection<String> varianteImagenes;
		private String extensionImagen;
		private OperationStatus status;

		public Builder() {
			varianteImagenes = new ArrayList<String>();
		}

		public Builder setPersonajes(Collection<Personaje> personajes) {
			this.personajes = personajes;
			return this;
		}

		public Builder setOperationstatus(OperationStatus opStatus) {
			this.status = opStatus;
			return this;
		}

		public Builder addVarianteImagen(String varianteImagen) {
			varianteImagenes.add(varianteImagen);
			return this;
		}

		public Builder setExtensionImagen(String extensionImagen) {
			this.extensionImagen = extensionImagen;
			return this;
		}

		public PersonajeGetResponse build() {
			PersonajeGetResponse response = new PersonajeGetResponse();

			if (personajes == null) {
				personajes = new ArrayList<Personaje>();
			}

			if (extensionImagen == null) {
				extensionImagen = DEFAULT_EXTENSION;
			}

			if (varianteImagenes == null || varianteImagenes.size() == 0) {
				varianteImagenes.add(DEFAULT_VARIANT);
			}
			List<InnerPersonaje> innerPersonajes = new ArrayList<InnerPersonaje>();

			for (Personaje p : personajes) {
				InnerPersonaje innerPersonaje = new InnerPersonaje();
				innerPersonaje.setId(p.getId());
				innerPersonaje.setNombre(p.getNombre());
				innerPersonaje.setDescripcion(p.getDescripcion());

				Map<String, String> mapaImagenes = new HashMap<String, String>();

				for (String variante : varianteImagenes) {
					String url = p.getImagen() + "/" + variante + "." + extensionImagen;
					mapaImagenes.put(variante, url);
				}

				innerPersonaje.setImagen(mapaImagenes);

				innerPersonajes.add(innerPersonaje);
			}

			response.setPersonajes(innerPersonajes);

			if (status == null) {
				status = new OperationStatus();
				status.setStatusCode(Status.OK);
				status.setMessage("");
			}

			response.setStatus(status);

			return response;
		}
	}

}
