package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje.PersonajeGetResponse.Builder;

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

	public static class Builder {

		private static final String DEFAULT_EXTENSION = "jpg";
		private static final String DEFAULT_VARIANT = "standard_large";

		private Grupo grupo;
		private Collection<Grupo> grupos;
		private OperationStatus operationStatus;
		private Dao<Personaje, FiltroPersonaje> personajeDao;
		private boolean expandirPersonajes;
		private Collection<String> varianteImagenes;
		private String extensionImagen;

		public Builder() {
			varianteImagenes = new ArrayList<String>();
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

		public Builder setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
			this.personajeDao = personajeDao;
			return this;
		}

		public Builder setExpandirPersonajes(boolean expandirPersonajes) {
			this.expandirPersonajes = expandirPersonajes;
			return this;
		}

		public Builder setVarianteImagenes(Collection<String> varianteImagenes) {
			this.varianteImagenes = varianteImagenes;
			return this;
		}

		public Builder setExtensionImagen(String extensionImagen) {
			this.extensionImagen = extensionImagen;
			return this;
		}

		public Builder addVarianteImagen(String varianteImagen) {
			varianteImagenes.add(varianteImagen);
			return this;
		}

		public GrupoGetResponse build() {
			GrupoGetResponse response = new GrupoGetResponse();

			if (grupos == null) {
				grupos = new HashSet<Grupo>();
			}

			if (grupo != null)
				grupos.add(grupo);

			Map<Long, InnerPersonaje> mapPersonajes = null;

			if (expandirPersonajes) {

				if (extensionImagen == null) {
					extensionImagen = DEFAULT_EXTENSION;
				}

				if (varianteImagenes == null || varianteImagenes.size() == 0) {
					varianteImagenes.add(DEFAULT_VARIANT);
				}

				mapPersonajes = new HashMap<>();
				FiltroPersonaje.Builder filtroPersonajeBuilder = new FiltroPersonaje.Builder();

				for (Grupo g : grupos) {
					filtroPersonajeBuilder.clear();

					Set<Long> idsPersonaje = new HashSet<>();

					for (Long idPersonaje : g.getPersonajes()) {
						if (!mapPersonajes.containsKey(idPersonaje))
							idsPersonaje.add(idPersonaje);
					}

					if (idsPersonaje.size() > 0) {
						filtroPersonajeBuilder.setIds(idsPersonaje);
						Collection<FiltroPersonaje> filtrosPersonaje = filtroPersonajeBuilder.build();
						Set<Personaje> personajes = personajeDao.find(filtrosPersonaje);

						for (Personaje p : personajes) {
							InnerPersonaje innerP = new InnerPersonaje();
							innerP.setId(p.getId());
							innerP.setDescripcion(p.getDescripcion());
							innerP.setNombre(p.getNombre());

							Map<String, String> mapaImagenes = new HashMap<String, String>();

							if (p.getImagen() != null && !p.getImagen().isEmpty()) {
								for (String variante : varianteImagenes) {
									String url = p.getImagen() + "/" + variante + "." + extensionImagen;
									mapaImagenes.put(variante, url);
								}
							}

							innerP.setImagen(mapaImagenes);

							mapPersonajes.put(innerP.getId(), innerP);
						}
					}
				}
			}

			Collection<InnerGrupo> innerGrupos = new HashSet<>();

			for (Grupo grupo : grupos) {

				InnerGrupo innerGrupo = new InnerGrupo();
				innerGrupo.setId(grupo.getId());
				innerGrupo.setName(grupo.getNombre());

				if (mapPersonajes != null) {

					Collection<InnerPersonaje> personajes = new HashSet<>();

					for (Long idPersonaje : grupo.getPersonajes()) {
						if (mapPersonajes.containsKey(idPersonaje)) {
							InnerPersonaje p = mapPersonajes.get(idPersonaje);
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
