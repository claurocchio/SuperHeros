package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.ranking;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class RankingGetResponse {

	private OperationStatus status;
	
	@JsonProperty("personajes")
	private Collection<String> personajes;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Collection<String> getPersonajes(){
		return this.personajes;
	}
	
	public void setPersonajes(Collection<String> personajes) {
		this.personajes = personajes;
	}

	public static class Builder {

		private List<Long> personajes;
		private Dao<Personaje, FiltroPersonaje> personajeDao;
		private OperationStatus operationStatus;

		public Builder setPersonajes(List<Long> personajes) {
			this.personajes = personajes;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public Builder setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
			this.personajeDao = personajeDao;
			return this;
		}
		
		public RankingGetResponse build() {
			RankingGetResponse response = new RankingGetResponse();

			if (personajes != null) {
				
				Collection<String> nombresPersonaje = new HashSet<>();
				FiltroPersonaje.Builder filtroPersonajeBuilder = new FiltroPersonaje.Builder();
				filtroPersonajeBuilder.clear();
				filtroPersonajeBuilder.setIds(personajes);
				//filtroPersonajeBuilder.setId(favoritos.iterator().next());
				Collection<FiltroPersonaje> filtrosPersonaje = filtroPersonajeBuilder.build();
				Set<Personaje> personajes = new HashSet<>();
				personajes = personajeDao.find(filtrosPersonaje);	
				for(Personaje p : personajes){
					nombresPersonaje.add(p.getNombre());
				}
				
				response.setPersonajes(nombresPersonaje);
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
