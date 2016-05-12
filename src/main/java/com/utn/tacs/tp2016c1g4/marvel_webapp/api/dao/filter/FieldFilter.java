package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.operation.FieldOperation;

public class FieldFilter {

	private String field;
	private FieldOperation operation;
	private Object value;

	public FieldFilter(String field, FieldOperation operation, Object value) {
		super();
		this.field = field;
		this.operation = operation;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public FieldOperation getOperation() {
		return operation;
	}

	public void setOperation(FieldOperation operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return operation + "(" + field + "," + value.toString() + ")";
	}

}
