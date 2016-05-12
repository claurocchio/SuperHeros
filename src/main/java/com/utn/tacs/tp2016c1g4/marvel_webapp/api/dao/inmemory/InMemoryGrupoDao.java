package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.field.GrupoField;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FieldFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class InMemoryGrupoDao extends InMemoryDao<Grupo> {

	@Override
	protected boolean passesFilter(Grupo obj, FieldFilter filter) {
		boolean passes = false;

		Object fieldValue = null;

		switch (GrupoField.fromString(filter.getField())) {
		case ID:
			fieldValue = obj.getId();
			break;
		case NOMBRE:
			fieldValue = obj.getNombre();
			break;
		}

		switch (filter.getOperation()) {
		case EQUALS:
			passes = fieldValue.equals(filter.getValue());
			break;
		case IN:
			FOR: for (Object inObj : (Object[]) filter.getValue()) {
				passes = passes || fieldValue.equals(inObj);
				if (passes == true)
					break FOR;
			}
			break;
		}

		return passes;
	}
}
