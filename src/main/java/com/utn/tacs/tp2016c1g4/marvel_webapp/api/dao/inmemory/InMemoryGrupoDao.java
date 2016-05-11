package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.field.GrupoField;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class InMemoryGrupoDao extends InMemoryDao<Grupo> {

	@Override
	protected boolean passesFilter(Grupo obj, SearchFilter filter) {
		boolean passes = false;

		GrupoField field = GrupoField.fromString(filter.getField());

		switch (field) {
		case ID:
			passes = obj.getId().equals(filter.getValue());
			break;
		case NOMBRE:
			passes = obj.getNombre().equals(filter.getValue());
			break;
		default:
			break;
		}

		return passes;
	}
}
