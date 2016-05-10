package com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.exception;

import java.util.Collection;

public class ManyResultsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<?> collection;

	public ManyResultsException(Collection<?> collection) {
		super("se obtuvo mas de un resultado");
		this.collection = collection;
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> getCollection(Class<T> type) {
		return (Collection<T>) collection;
	}
}
