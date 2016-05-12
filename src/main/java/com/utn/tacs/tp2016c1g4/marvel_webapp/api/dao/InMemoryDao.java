package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FieldFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FilterCollection;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.operation.FilterCollectionOperation;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PersonajesResource;

public abstract class InMemoryDao<T extends Entity> implements Dao<T> {

	private static final Logger logger = LogManager.getLogger(InMemoryDao.class);

	private Set<T> collection;
	private long lastInsertedId;

	public InMemoryDao() {
		collection = new HashSet<T>();
		this.clear();
	}

	@Override
	public Set<T> getAll() {
		return collection;
	}

	protected abstract boolean passesFilter(T obj, FieldFilter filter);

	@Override
	public Set<T> find(FieldFilter filter) {
		FilterCollection filterCol = new FilterCollection(FilterCollectionOperation.CONJUNCTION, filter);
		return find(filterCol);
	}

	@Override
	public Set<T> find(FilterCollection filterCol) {

		Set<T> results = null;

		logger.debug("filtrando por: " + filterCol);

		switch (filterCol.getOperation()) {
		case CONJUNCTION:
			results = findMatchingAll(filterCol.getFilters());
			break;
		case DISJUNCTION:
			results = findMatchingAny(filterCol.getFilters());
			break;
		}

		return results;
	}

	private Set<T> findMatchingAll(List<FieldFilter> filters) {
		boolean passes = true;
		List<T> results = new ArrayList<T>();

		for (T obj : collection) {
			for (FieldFilter f : filters) {
				passes = passes && passesFilter(obj, f);
				if (passes == false)
					break;
			}

			if (passes)
				results.add(obj);
		}

		return new HashSet<T>(results);
	}

	private Set<T> findMatchingAny(List<FieldFilter> filters) {
		boolean passes = false;
		List<T> results = new ArrayList<T>();

		for (T obj : collection) {
			for (FieldFilter f : filters) {
				passes = passes || passesFilter(obj, f);
				if (passes == true)
					break;
			}

			if (passes)
				results.add(obj);
		}

		return new HashSet<T>(results);
	}

	@Override
	public T findOne(FilterCollection filterCol) {
		Set<T> results = find(filterCol);

		if (results.size() > 1)
			throw new ManyResultsException(results);

		return results.size() > 0 ? results.iterator().next() : null;
	}

	@Override
	public T findOne(FieldFilter filter) throws ManyResultsException {
		FilterCollection filterCol = new FilterCollection(FilterCollectionOperation.CONJUNCTION, filter);
		return findOne(filterCol);
	}

	public void setCollection(Set<T> collection) {
		this.collection = collection;
	}

	public void save(T obj) {
		obj.setId(++lastInsertedId);
		this.collection.add(obj);
	}

	public void clear() {
		this.collection.clear();
		lastInsertedId = 0L;
	}
}
