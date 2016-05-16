package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public abstract class InMemoryAbstractDao<T extends Entity, F extends SearchFilter<T>> implements Dao<T, F> {

	private Set<T> collection;
	private long nextId;

	public InMemoryAbstractDao() {
		super();
		this.collection = new HashSet<>();
		this.clear();
	}

	@Override
	public Set<T> getAll() {
		return new HashSet<>(collection);
	}

	@Override
	public Set<T> find(Collection<F> filters) {
		List<T> filtered = new ArrayList<>();

		for (T obj : collection) {
			boolean passes = true;

			for (F filter : filters) {
				passes = passes && passesFilter(filter, obj);

				if (!passes)
					break;
			}

			if (passes)
				filtered.add(obj);
		}

		return new HashSet<>(filtered);
	}

	protected abstract boolean passesFilter(F filter, T obj);

	@Override
	public T findOne(Collection<F> filters) {
		Set<T> results = find(filters);

		if (results.size() > 1) {
			throw new ManyResultsException(results);
		}

		return results.size() == 1 ? results.iterator().next() : null;
	}

	@Override
	public boolean save(T obj) {
		obj.setId(this.nextId);
		boolean saved = this.collection.add(obj);
		if(saved) {	
			this.nextId++;
		}
		return saved;
	}
	
	@Override
	public boolean delete(T obj) {
		return this.collection.remove(obj);
	}

	public void clear() {
		this.collection.clear();
		this.nextId = 1;
	}
}
