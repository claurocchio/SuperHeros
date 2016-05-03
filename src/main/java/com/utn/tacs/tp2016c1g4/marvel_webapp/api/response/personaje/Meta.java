package com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.personaje;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Meta {
	private Map<String, Object> otherProperties = new HashMap<String, Object>();

	public Meta(){
		
	}
	public Map<String, Object> getOtherProperties() {
		return otherProperties;
	}
    @JsonAnySetter
	public void setOtherProperties(Map<String, Object> otherProperties) {
		this.otherProperties = otherProperties;
	}
}
