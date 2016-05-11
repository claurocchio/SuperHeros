package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public abstract class InMemoryDao<T extends Entity> implements Dao<T> {

	private Set<T> collection;
	private Long lastInsertedId;

	public InMemoryDao() {
		collection = new HashSet<T>();
		this.clear();
	}

	@Override
	public Set<T> getAll() {
		return collection;
	}

	protected abstract boolean passesFilter(T obj, SearchFilter filter);

	@Override
	public Set<T> find(SearchFilter filter) {
		List<SearchFilter> list = new ArrayList<SearchFilter>();
		list.add(filter);
		return find(list);
	}

	@Override
	public Set<T> find(List<SearchFilter> filters) {
		List<T> results = new ArrayList<T>();

		for (T obj : collection) {
			boolean passes = true;
			for (SearchFilter f : filters) {
				passes = passes && passesFilter(obj, f);

				if (passes == false)
					break;
			}
			if (passes)
				results.add(obj);
		}

		return new HashSet<T>(results);
	}

	@Override
	public T findOne(List<SearchFilter> filters) {
		Set<T> results = find(filters);

		if (results.size() > 1)
			throw new ManyResultsException(results);

		return results.size() > 0 ? results.iterator().next() : null;
	}

	@Override
	public T findOne(SearchFilter filter) throws ManyResultsException {
		List<SearchFilter> list = new ArrayList<SearchFilter>();
		list.add(filter);
		return findOne(list);
	}

	public void setCollection(Set<T> collection) {
		this.collection = collection;
	}

	public void add(T obj) {
		obj.setId(++lastInsertedId);
		this.collection.add(obj);
	}

	public void clear() {
		this.collection.clear();
		lastInsertedId = 0L;
	}
}
