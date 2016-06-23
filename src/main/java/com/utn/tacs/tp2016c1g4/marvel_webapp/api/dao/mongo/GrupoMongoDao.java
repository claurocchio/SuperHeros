package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.MongoDBAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

@Singleton
public class GrupoMongoDao extends MongoDBAbstractDao<Grupo, FiltroGrupo> {

	@SuppressWarnings("unchecked")
	@Override
	protected <T> Map<String, ?> createFilters(Collection<FiltroGrupo> filters) {
		Map<String, T> mapFilters = new HashMap<String, T>();

		for (FiltroGrupo f : filters) {
			switch (f.getTipo()) {
			case ID:
				mapFilters.put("_id", (T) f.getValue());
				break;
			case NAME:
				mapFilters.put("nombre", (T) f.getValue());
				break;
			}
		}

		return mapFilters;
	}

	@Override
	protected Grupo fullClone(Grupo from) {
		Grupo into = new Grupo();
		// se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));

		// la coleccion debe copiarse uno por una tambien
		Set<Long> personajes = new HashSet<>();
		if (from.getPersonajes() != null) {
			for (Long item : from.getPersonajes())
				personajes.add(item);
		}
		into.setPersonajes(personajes);
		return into;
	}

	@Override
	protected void cloneForUpdate(Grupo from, Grupo into) {
		// se copian uno por uno los atributos
		// into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));

		// la coleccion debe copiarse uno por una tambien
		Set<Long> personajes = new HashSet<>();
		if (from.getPersonajes() != null) {
			for (Long item : from.getPersonajes())
				personajes.add(item);
		}
		into.setPersonajes(personajes);
	}

}
