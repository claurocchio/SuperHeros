package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerFavorito;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerPersonaje;

public class FavoritoGetResponse {

	@JsonProperty("favoritos")
	
	private InnerFavorito favorito;
	private OperationStatus status;

	public InnerFavorito getFavorito() {
		return favorito;
	}

	public void setFavorito(InnerFavorito favorito) {
		this.favorito = favorito;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}
	
	public static class Builder {

		private static final String DEFAULT_EXTENSION = "jpg";
		private static final String DEFAULT_VARIANT = "standard_large";

		private Collection<Long> favoritos;
		private OperationStatus operationStatus;
		private Dao<Personaje, FiltroPersonaje> personajeDao;
		private boolean expandirPersonajes;
		private Collection<String> varianteImagenes;	
		private String extensionImagen;

		public Builder() {
			varianteImagenes = new ArrayList<String>();
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public Builder setFavoritos(Collection<Long> favoritos) {
			this.favoritos = favoritos;
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

		public FavoritoGetResponse build() {
			FavoritoGetResponse response = new FavoritoGetResponse();

			Map<Long, InnerPersonaje> mapPersonajes = null;
			Collection<String> nombresPersonaje = new HashSet<>();
			
			if (favoritos != null && !favoritos.isEmpty()) {
				if (expandirPersonajes) {

					if (extensionImagen == null) {
						extensionImagen = DEFAULT_EXTENSION;
					}

					if (varianteImagenes == null || varianteImagenes.size() == 0) {
						varianteImagenes.add(DEFAULT_VARIANT);
					}

					mapPersonajes = new HashMap<>();
					FiltroPersonaje.Builder filtroPersonajeBuilder = new FiltroPersonaje.Builder();

					
					filtroPersonajeBuilder.clear();

					Set<Long> idsPersonaje = new HashSet<>();

					for (Long idPersonaje : favoritos) {
						if (!mapPersonajes.containsKey(idPersonaje))//no deberia ser necesario / no se repiten favoritos
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
				}else{
					for(Long p : favoritos){
						FiltroPersonaje.Builder filtroPersonajeBuilder = new FiltroPersonaje.Builder();
						filtroPersonajeBuilder.clear();
						filtroPersonajeBuilder.setId(p);
						//filtroPersonajeBuilder.setId(favoritos.iterator().next());
						Collection<FiltroPersonaje> filtrosPersonaje = filtroPersonajeBuilder.build();
						Personaje personaje = null;
						personaje = personajeDao.findOne(filtrosPersonaje);	
					
						nombresPersonaje.add(personaje.getNombre());
					}
				}
			}
			
			InnerFavorito innerFavorito = new InnerFavorito();

			if (mapPersonajes != null) {

				Collection<InnerPersonaje> personajes = new HashSet<>();

				for (Long idPersonaje : favoritos) {
					if (mapPersonajes.containsKey(idPersonaje)) {
						InnerPersonaje p = mapPersonajes.get(idPersonaje);
						personajes.add(p);
					}
				}

				innerFavorito.setPersonajes(personajes);
			} else {
				innerFavorito.setPersonajes(nombresPersonaje);
			}		

			response.setFavorito(innerFavorito);

			if (operationStatus == null) {
				operationStatus = new OperationStatus();
				operationStatus.setStatusCode(Status.OK);
			}

			response.setStatus(operationStatus);
			return response;
		}
		
		
	}
}
