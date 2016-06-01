package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import java.util.HashSet;
import java.util.Set;

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

	@Override
	protected Grupo fullClone(Grupo from) {
		Grupo into = new Grupo();
		//se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));
		
		//la coleccion debe copiarse uno por una tambien
		Set<Long> personajes = new HashSet<>();
		if(from.getPersonajes() != null){
			for(Long item : from.getPersonajes()) personajes.add(item);
		}
		into.setPersonajes(personajes);
		return into; 
	}

	@Override
	protected void cloneForUpdate(Grupo from, Grupo into) {
		//se copian uno por uno los atributos
		//into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));
				
		//la coleccion debe copiarse uno por una tambien
		Set<Long> personajes = new HashSet<>();
		if(from.getPersonajes() != null){
			for(Long item : from.getPersonajes()) personajes.add(item);
		}
		into.setPersonajes(personajes);	
	}

}
