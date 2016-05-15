package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import java.util.Collection;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;

@Singleton
public class PersonajeInMemoryDao extends InMemoryAbstractDao<Personaje, FiltroPersonaje> {

	@Override
	protected boolean passesFilter(FiltroPersonaje filter, Personaje obj) {
		boolean passes = true;

		switch (filter.getTipo()) {
		case ID:
			Long value = filter.getValue();
			passes = value.equals(obj.getId());
			break;
		case IDS:
			boolean exists = false;
			Collection<Long> ids = filter.getValue();
			for (Long currId : ids) {
				if (obj.getId().equals(currId)) {
					exists = true;
					break;
				}
			}
			passes = exists;
			break;
		case NOMBRE:
			String nombre = filter.getValue();
			passes = nombre.equals(obj.getName());
			break;
		}

		return passes;
	}

}
