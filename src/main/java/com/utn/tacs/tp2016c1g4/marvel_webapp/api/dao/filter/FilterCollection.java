package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.ArrayList;
import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.operation.FilterCollectionOperation;

public class FilterCollection {

	private List<FieldFilter> filters;
	private FilterCollectionOperation operation;

	public FilterCollection(FilterCollectionOperation operation, FieldFilter... filters) {
		this.operation = operation;

		this.filters = new ArrayList<FieldFilter>();

		for (FieldFilter filter : filters) {
			this.filters.add(filter);
		}
	}

	public List<FieldFilter> getFilters() {
		return filters;
	}

	public FilterCollectionOperation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return operation + filters.toString();
	}

}
