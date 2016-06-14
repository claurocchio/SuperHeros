package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import java.util.Collection;
import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

@Singleton
public class UsuarioInMemoryDao extends InMemoryAbstractDao<Usuario, FiltroUsuario> {

	@Override
	protected boolean passesFilter(FiltroUsuario filter, Usuario obj) {
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
		case IDPERFIL:
			Long idPerfil = filter.getValue();
			passes = idPerfil.equals(obj.getIdPerfil());
			break;
		case USERNAME:
			String userName = filter.getValue();
			passes = userName.equals(obj.getUserName());
			break;
		}

		return passes;
	}

	@Override
	protected Usuario fullClone(Usuario from) {
		Usuario into = new Usuario();
		//se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setIdPerfil(new Long(from.getIdPerfil()));
		into.setPass(new String(from.getPass()));
		
		return into; 
	}

	@Override
	protected void cloneForUpdate(Usuario from, Usuario into) {
		//se copian uno por uno los atributos
		//into.setId(new Long(from.getId()));
		into.setIdPerfil(new Long(from.getIdPerfil()));
		into.setPass(new String(from.getPass()));
				
	}

}
