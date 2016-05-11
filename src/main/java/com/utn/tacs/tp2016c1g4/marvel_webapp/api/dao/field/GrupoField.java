package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.field;

public enum GrupoField {
	ID("id"), NOMBRE("nombre");

	private String name;

	GrupoField(String name) {
		this.name = name;
	}

	public static GrupoField fromString(String fieldName) {
		GrupoField field = null;
		for (GrupoField f : GrupoField.values()) {
			if (f.name == fieldName) {
				field = f;
				break;
			}
		}
		return field;
	}
}
