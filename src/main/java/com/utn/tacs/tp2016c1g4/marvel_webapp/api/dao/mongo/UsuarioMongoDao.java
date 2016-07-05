package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.MongoDBAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

@Singleton
public class UsuarioMongoDao extends MongoDBAbstractDao<Usuario, FiltroUsuario> {

	@SuppressWarnings("unchecked")
	@Override
	protected <T> Map<String, ?> createFilters(Collection<FiltroUsuario> filters) {
		Map<String, T> mapFilters = new HashMap<String, T>();

		for (FiltroUsuario f : filters) {
			switch (f.getTipo()) {
			case ID:
				mapFilters.put("_id", (T) f.getValue());
				break;
			case IDS:
				ArrayList<BasicDBObject> idList = new ArrayList<BasicDBObject>();
				for (Long id : (ArrayList<Long>) f.getValue()) {
					BasicDBObject query = new BasicDBObject("_id", id);
					idList.add(query);
				}
				mapFilters.put("$or", (T) idList);
				break;
			case IDPERFIL:
				mapFilters.put("idPerfil", (T) f.getValue());
				break;
			case USERNAME:
				mapFilters.put("userName", (T) f.getValue());
				break;
			}
		}

		return mapFilters;
	}

	@Override
	protected Usuario fullClone(Usuario from) {
		Usuario into = new Usuario();
		// se copian uno por uno los atributos
		into.setUserName(from.getUserName());
		into.setId(new Long(from.getId()));
		into.setIdPerfil(new Long(from.getIdPerfil()));
		into.setEmail(from.getEmail());
		into.setPass(new String(from.getPass()));
		into.setAdmin(from.isAdmin());
		into.setLastAccess(from.getLastAccess());
		return into;
	}

	@Override
	protected void cloneForUpdate(Usuario from, Usuario into) {
		// se copian uno por uno los atributos
		// into.setId(new Long(from.getId()));
		into.setIdPerfil(new Long(from.getIdPerfil()));
		into.setPass(new String(from.getPass()));
		into.setUserName(from.getUserName());
		into.setEmail(from.getEmail());
		into.setAdmin(from.isAdmin());
		into.setLastAccess(from.getLastAccess());
	}

}
