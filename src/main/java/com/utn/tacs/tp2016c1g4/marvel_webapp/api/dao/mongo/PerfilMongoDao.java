package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.MongoDBAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

@Singleton
public class PerfilMongoDao extends MongoDBAbstractDao<Perfil, FiltroPerfil> {

	@SuppressWarnings("unchecked")
	@Override
	protected <T> Map<String, ?> createFilters(Collection<FiltroPerfil> filters) {
		Map<String, T> mapFilters = new HashMap<String, T>();

		for (FiltroPerfil f : filters) {
			switch (f.getTipo()) {
			case ID:
				mapFilters.put("_id", (T) f.getValue());
				break;
			case USERNAME:
				mapFilters.put("username", (T) f.getValue());
				break;
			}
		}

		return mapFilters;
	}

	@Override
	protected Perfil fullClone(Perfil from) {
		Perfil into = new Perfil();
		// se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setUsername(new String(from.getUsername()));
		into.setEmail(from.getEmail());

		// la coleccion debe copiarse uno por una tambien
		Set<Long> grupos = new HashSet<>();
		if (from.getIdGrupos() != null) {
			for (Long item : from.getIdGrupos())
				grupos.add(item);
		}
		into.setIdGrupos(grupos);

		Set<Long> favs = new HashSet<>();
		if (from.getIdsPersonajesFavoritos() != null) {
			for (Long item : from.getIdsPersonajesFavoritos())
				favs.add(item);
		}
		into.setIdsPersonajesFavoritos(favs);
		return into;
	}

	@Override
	protected void cloneForUpdate(Perfil from, Perfil into) {
		// se copian uno por uno los atributos
		// into.setId(new Long(from.getId()));
		into.setUsername(new String(from.getUsername()));

		// la coleccion debe copiarse uno por una tambien
		Set<Long> grupos = new HashSet<>();
		if (from.getIdGrupos() != null) {
			for (Long item : from.getIdGrupos())
				grupos.add(item);
		}
		into.setIdGrupos(grupos);

		Set<Long> favs = new HashSet<>();
		if (from.getIdsPersonajesFavoritos() != null) {
			for (Long item : from.getIdsPersonajesFavoritos())
				favs.add(item);
		}
		into.setIdsPersonajesFavoritos(favs);
	}

}
