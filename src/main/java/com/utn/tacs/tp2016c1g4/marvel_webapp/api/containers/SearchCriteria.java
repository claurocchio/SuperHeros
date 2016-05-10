package com.utn.tacs.tp2016c1g4.marvel_webapp.api.containers;

public interface SearchCriteria<T> {

	boolean passes(T obj);

}
