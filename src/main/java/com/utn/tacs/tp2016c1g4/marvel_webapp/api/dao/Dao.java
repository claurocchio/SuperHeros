package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.List;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public interface Dao<T extends Entity> {
	Set<T> getAll();

	Set<T> find(SearchFilter filter);

	Set<T> find(List<SearchFilter> filters);

	T findOne(SearchFilter filter);

	T findOne(List<SearchFilter> filters);

	void add(T obj);

}
