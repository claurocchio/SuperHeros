package com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.Repository;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.SearchCriteria;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers.exception.ManyResultsException;

@Singleton
public class InMemoryRepository<T> implements Repository<T> {

	private Set<T> collection;

	public InMemoryRepository() {
		collection = new HashSet<T>();
	}

	@Override
	public Set<T> getAll() {
		return collection;
	}

	@Override
	public Set<T> find(SearchCriteria<T> searchCriteria) {
		List<T> results = new ArrayList<T>();

		for (T obj : collection) {
			if (passesCriteria(obj, searchCriteria))
				results.add(obj);
		}

		return new HashSet<T>(results);
	}

	private boolean passesCriteria(T obj, SearchCriteria<T> searchCriteria) {
		return searchCriteria.passes(obj);
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

	public void add(T obj) {
		this.collection.add(obj);
	}

}
