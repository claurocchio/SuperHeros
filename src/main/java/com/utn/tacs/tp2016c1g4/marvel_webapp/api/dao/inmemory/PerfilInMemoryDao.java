package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

@Singleton
public class PerfilInMemoryDao extends InMemoryAbstractDao<Perfil, FiltroPerfil> {

	@Override
	protected boolean passesFilter(FiltroPerfil filter, Perfil perfil) {
		boolean passes = false;

		switch (filter.getTipo()) {
		case ID:
			Long id = filter.getValue();
			passes = perfil.getId().equals(id);
			break;
		case USERNAME:
			String username = filter.getValue();
			passes = perfil.getUserName().equals(username);
			break;
		}

		return passes;
	}

}
