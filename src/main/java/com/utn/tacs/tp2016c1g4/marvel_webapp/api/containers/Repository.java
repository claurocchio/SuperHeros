package com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers;

import java.util.Set;

public interface Repository<T> {

	Set<T> getAll();

	Set<T> find(SearchCriteria<T> searchCriteria);

	T findOne(SearchCriteria<T> searchCriteria);
}
