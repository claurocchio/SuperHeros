package com.utn.tacs.tp2016c1g4.marvel_webapp.api.business;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Personaje {
	@JsonProperty
	private int id;
	@JsonProperty
	private String name;
	
	private Map<String, Object> otherProperties = new HashMap<String, Object>();

	public Personaje() {

	}

	public Personaje(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }
    
    @Override
    public String toString() {
        return "Personaje [name=" + name + ", id=" + id + "]";
    }
}
