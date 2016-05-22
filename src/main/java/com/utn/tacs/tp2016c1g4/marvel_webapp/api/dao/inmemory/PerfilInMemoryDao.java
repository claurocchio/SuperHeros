package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.InMemoryAbstractDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
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

	@Override
	protected Perfil fullClone(Perfil from) {
		Perfil into = new Perfil();
		//se copian uno por uno los atributos
		into.setId(new Long(from.getId()));
		into.setUserName(new String(from.getUserName()));
		
		//la coleccion debe copiarse uno por una tambien
		Set<Long> grupos = new HashSet<>();
		if(from.getIdGrupos() != null){
			for(Long item : from.getIdGrupos()) grupos.add(item);
		}
		into.setIdGrupos(grupos);
		
		Set<Long> favs = new HashSet<>();
		if(from.getIdsPersonajesFavoritos() != null){
			for(Long item : from.getIdsPersonajesFavoritos()) favs.add(item);
		}
		into.setIdsPersonajesFavoritos(favs);
		return into; 
	}

}
