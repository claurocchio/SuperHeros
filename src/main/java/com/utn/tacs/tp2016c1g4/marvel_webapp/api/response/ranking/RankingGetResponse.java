package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.ranking;


import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;

@JsonInclude(Include.NON_NULL)
public class RankingGetResponse {

	private OperationStatus status;
	
	@JsonProperty("personajes")
	private List<Long> personajes;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public List<Long> getPersonajes(){
		return this.personajes;
	}
	
	public void setPersonajes(List<Long> personajes) {
		this.personajes = personajes;
	}

	public static class Builder {

		private List<Long> personajes;
		
		private OperationStatus operationStatus;

		public Builder setPersonajes(List<Long> personajes) {
			this.personajes = personajes;
			return this;
		}

		public Builder setOperationStatus(OperationStatus operationStatus) {
			this.operationStatus = operationStatus;
			return this;
		}

		public RankingGetResponse build() {
			RankingGetResponse response = new RankingGetResponse();

			if (personajes != null) {
				response.setPersonajes(personajes);
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
