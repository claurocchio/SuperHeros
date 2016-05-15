package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

@Singleton
public class GrupoInMemoryDao extends InMemoryAbstractDao<Grupo, FiltroGrupo> {

	@Override
	protected boolean passesFilter(FiltroGrupo filter, Grupo grupo) {
		boolean passes = false;

		switch (filter.getTipo()) {
		case ID:
			Long id = filter.getValue();
			passes = grupo.getId().equals(id);
			break;
		case NAME:
			String name = filter.getValue();
			passes = grupo.getNombre().equals(name);
			break;
		}

		return passes;
	}

}
