package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.inject.Singleton;

import com.mongodb.BasicDBObject;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.MongoDBAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;

@Singleton
public class PersonajeMongoDao extends MongoDBAbstractDao<Personaje, FiltroPersonaje> {

	@SuppressWarnings("unchecked")
	@Override
	protected <T> Map<String, ?> createFilters(Collection<FiltroPersonaje> filters) {
		Map<String, T> mapFilters = new HashMap<String, T>();

		if (filters == null) {
			filters = new ArrayList<>();
		}

		for (FiltroPersonaje f : filters) {
			switch (f.getTipo()) {
			case ID:
				mapFilters.put("_id", (T) f.getValue());
				break;
			case IDS:
				ArrayList<BasicDBObject> idList = new ArrayList<BasicDBObject>();
				for (Long id : (HashSet<Long>) f.getValue()) {
					BasicDBObject query = new BasicDBObject("_id", id);
					idList.add(query);
				}
				mapFilters.put("$or", (T) idList);
				break;
			case NOMBRE:
				mapFilters.put("nombre", (T) f.getValue());
				break;
			}
		}

		return mapFilters;
	}

	@Override
	protected Personaje fullClone(Personaje from) {
		Personaje into = new Personaje();

		// se copian uno por uno los atributos
		into.setId(new Long(from.getId()));

		if (from.getNombre() != null)
			into.setNombre(new String(from.getNombre()));

		if (from.getImagen() != null)
			into.setImagen(new String(from.getImagen()));

		if (from.getDescripcion() != null)
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
