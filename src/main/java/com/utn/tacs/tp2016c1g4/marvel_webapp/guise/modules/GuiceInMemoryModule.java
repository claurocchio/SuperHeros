package com.utn.tacs.tp2016c1g4.marvel_webapp.guise.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.GrupoInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PersonajeInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.UsuarioInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

public class GuiceInMemoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<Dao<Perfil, FiltroPerfil>>() {
		}).to(PerfilInMemoryDao.class);
		
		bind(new TypeLiteral<Dao<Grupo, FiltroGrupo>>() {
		}).to(GrupoInMemoryDao.class);
		
		bind(new TypeLiteral<Dao<Personaje, FiltroPersonaje>>() {
		}).to(PersonajeInMemoryDao.class);
		
		bind(new TypeLiteral<Dao<Usuario, FiltroUsuario>>() {
		}).to(UsuarioInMemoryDao.class);
	}
}
