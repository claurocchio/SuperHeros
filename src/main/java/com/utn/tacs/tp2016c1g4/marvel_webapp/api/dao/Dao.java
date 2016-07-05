package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.Collection;
import java.util.Set;

import org.glassfish.jersey.spi.Contract;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public interface Dao<T extends Entity, F extends SearchFilter<T>> {
	Set<T> getAll();

	Set<T> find(Collection<F> filters);

	Set<T> find(Collection<F> filters, Page page);

	T findOne(Collection<F> filters);

	boolean save(T obj);

	boolean delete(T obj);

	boolean update(T obj);

	void clear();

	Long count();

	Long count(Collection<F> filters);
}
