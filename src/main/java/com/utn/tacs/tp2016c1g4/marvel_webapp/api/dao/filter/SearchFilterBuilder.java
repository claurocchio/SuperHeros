package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterBuilder {

	private List<SearchFilter> filters;

	public SearchFilterBuilder include(final String field, final Object value) {
		this.getFilters().add(new SearchFilter() {

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public String getField() {
				return field;
			}
		});

		return this;
	}

	private List<SearchFilter> getFilters() {
		if (filters == null)
			filters = new ArrayList<SearchFilter>();

		return filters;
	}

	public List<SearchFilter> build() {
		return filters;
	}

	public void clear() {
		getFilters().clear();
	}

}
