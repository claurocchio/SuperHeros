package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.entity.InnerGrupo;
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

	@JsonInclude(Include.NON_NULL)
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

		private Perfil perfil;
		private OperationStatus operationStatus;

		private boolean expandirGrupos;

		private Dao<Grupo, FiltroGrupo> grupoDao;

		public Builder setPerfil(Perfil perfil) {
			this.perfil = perfil;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public Builder setExpandirGrupos(boolean expandirGrupos) {
			this.expandirGrupos = expandirGrupos;
			return this;
		}

		public Builder setGrupoDao(Dao<Grupo, FiltroGrupo> grupoDao) {
			this.grupoDao = grupoDao;
			return this;
		}

		public PerfilGetResponse build() {
			PerfilGetResponse response = new PerfilGetResponse();

			if (perfil != null) {

				InnerPerfil innerPerfil = new InnerPerfil();
				innerPerfil.setId(perfil.getId());
				innerPerfil.setUsername(perfil.getUsername());
				innerPerfil.setEmail(perfil.getEmail());

				if (perfil.getIdGrupos() != null) {

					if (expandirGrupos) {

						List<InnerGrupo> innerGrupos = new ArrayList<>();

						for (Long idGrupo : perfil.getIdGrupos()) {

							FiltroGrupo.Builder filtroBuilder = new FiltroGrupo.Builder();
							filtroBuilder.setId(idGrupo);
							Set<FiltroGrupo> filtros = filtroBuilder.build();

							Grupo grupo = null;
							try {
								grupo = grupoDao.findOne(filtros);
							} catch (ManyResultsException e) {

							}

							if (grupo != null) {
								InnerGrupo innerGrupo = new InnerGrupo();
								innerGrupo.setId(grupo.getId());
								innerGrupo.setName(grupo.getNombre());
								innerGrupos.add(innerGrupo);
							}
						}
						innerPerfil.setGrupos(innerGrupos);
					} else {
						innerPerfil.setGrupos(perfil.getIdGrupos());
					}
				}

				if (perfil.getIdsPersonajesFavoritos() != null) {
					innerPerfil.setFavoritos(perfil.getIdsPersonajesFavoritos());
				}

				if (innerPerfil.getFavoritos() == null)
					innerPerfil.setFavoritos(new ArrayList<Object>());

				if (innerPerfil.getGrupos() == null)
					innerPerfil.setGrupos(new ArrayList<>());

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
