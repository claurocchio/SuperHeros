package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public interface Dao<T extends Entity, F extends SearchFilter<T>> {
	Set<T> getAll();

	Set<T> find(Collection<F> filters);

	T findOne(Collection<F> filters);

	void save(T obj);

}
