package com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain;

import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public class Personaje implements Entity {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String name;

	private Map<String, Object> otherProperties = new HashMap<String, Object>();
	
	public Personaje() {

	}

	public Personaje(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonAnySetter
	public void setOtherProperties(String name, Object value) {
		otherProperties.put(name, value);
	}
	
	@JsonAnyGetter
	public Map<String, Object> getOtherProperties() {
		return otherProperties;
	}
	
	@JsonAnyGetter
	public Object getProperty(String key){
		return otherProperties.get(key);
	}

	@Override
	public String toString() {
		return "Personaje [name=" + name + ", id=" + id + "]";
	}
}
