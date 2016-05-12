package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FieldFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FilterCollection;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public interface Dao<T extends Entity> {
	Set<T> getAll();

	Set<T> find(FieldFilter filter);

	Set<T> find(FilterCollection filterCol);

	T findOne(FieldFilter filter);

	T findOne(FilterCollection filters);

	void save(T obj);

}
