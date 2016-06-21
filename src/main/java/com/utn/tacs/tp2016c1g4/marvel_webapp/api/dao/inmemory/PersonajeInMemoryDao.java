package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

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
			passes = nombre.equals(obj.getNombre());
			break;
		}

		return passes;
	}

	@Override
	protected Personaje fullClone(Personaje from) {
		Personaje into = new Personaje();

		// se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));
		into.setImagen(new String(from.getImagen()));
		into.setDescripcion(new String(from.getDescripcion()));

		return into;
	}

	@Override
	protected void cloneForUpdate(Personaje from, Personaje into) {
		// se copian uno por uno los atributos
		// into.setId(new Long(from.getId()));
		into.setNombre(new String(from.getNombre()));
		into.setImagen(new String(from.getImagen()));
		into.setDescripcion(new String(from.getDescripcion()));
	}

}
