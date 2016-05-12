package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.operation.FieldOperation;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.operation.FilterCollectionOperation;

public class FilterSugaring {

	public static FieldFilter eq(String key, Object value) {
		return new FieldFilter(key, FieldOperation.EQUALS, value);
	}

	public static FieldFilter in(String key, Object... values) {
		return new FieldFilter(key, FieldOperation.IN, values);
	}

	public static FilterCollection disjunction(FieldFilter... filters) {
		return new FilterCollection(FilterCollectionOperation.DISJUNCTION, filters);
	}

	public static FilterCollection conjunction(FieldFilter... filters) {
		return new FilterCollection(FilterCollectionOperation.CONJUNCTION, filters);
	}

}
