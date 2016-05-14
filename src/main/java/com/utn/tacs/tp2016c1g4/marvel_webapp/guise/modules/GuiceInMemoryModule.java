package com.utn.tacs.tp2016c1g4.marvel_webapp.guise.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

public class GuiceInMemoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<Dao<Perfil, FiltroPerfil>>() {
		}).to(PerfilInMemoryDao.class);
	}
}
