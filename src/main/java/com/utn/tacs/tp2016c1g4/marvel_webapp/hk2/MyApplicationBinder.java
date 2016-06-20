package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.GrupoInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PersonajeInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;

public class MyApplicationBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(GrupoInMemoryDao.class).to(new TypeLiteral<Dao<Grupo, FiltroGrupo>>() {
		}).in(Singleton.class);
		bind(PerfilInMemoryDao.class).to(new TypeLiteral<Dao<Perfil, FiltroPerfil>>() {
		}).in(Singleton.class);;
		bind(PersonajeInMemoryDao.class).to(new TypeLiteral<Dao<Personaje, FiltroPersonaje>>() {
		}).in(Singleton.class);;
	}
}
