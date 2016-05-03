package com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.impl;

import java.util.HashSet;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Container;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.SearchCriteria;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.exception.ManyResultsException;

public class MemoryContainer<T> implements Container<T> {

	private Set<T> collection;

	public MemoryContainer() {
		collection = new HashSet<T>();
	}

	@Override
	public Set<T> getAll() {
		return collection;
	}

	@Override
	public Set<T> find(SearchCriteria<T> searchCriteria) {
		return null;
	}

	@Override
	public T findOne(SearchCriteria<T> searchCriteria) throws ManyResultsException {

		Set<T> results = find(searchCriteria);

		if (results.size() > 1)
			throw new ManyResultsException(results);

		return results.size() > 0 ? results.iterator().next() : null;
	}

	public void setCollection(Set<T> collection) {
		this.collection = collection;
	}

}
