package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerPersonaje;

public class PersonajeGetResponse {

	private Collection<InnerPersonaje> personajes;
	private OperationStatus status;
	private Page page;

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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public static class Builder {

		private static final String DEFAULT_EXTENSION = "jpg";
		private static final String DEFAULT_VARIANT = "standard_large";

		private Collection<Personaje> personajes;
		private Collection<String> varianteImagenes;
		private String extensionImagen;
		private OperationStatus status;
		private Page page;

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

		public Builder setPage(Page page) {
			this.page = page;
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

				if (p.getImagen() != null && !p.getImagen().isEmpty()) {
					for (String variante : varianteImagenes) {
						String url = p.getImagen() + "/" + variante + "." + extensionImagen;
						mapaImagenes.put(variante, url);
					}
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

			if (page != null) {
				response.setPage(page);
			}

			return response;
		}
	}

}
