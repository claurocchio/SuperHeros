package com.utn.tacs.tp2016c1g4.marvel_webapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.GrupoInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PersonajeInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.UsuarioInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo.GrupoMongoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo.PerfilMongoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo.PersonajeMongoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo.UsuarioMongoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

public class MongoDaoBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(GrupoMongoDao.class).to(new TypeLiteral<Dao<Grupo, FiltroGrupo>>() {
		}).in(Singleton.class);
		bind(PerfilMongoDao.class).to(new TypeLiteral<Dao<Perfil, FiltroPerfil>>() {
		}).in(Singleton.class);
		bind(PersonajeMongoDao.class).to(new TypeLiteral<Dao<Personaje, FiltroPersonaje>>() {
		}).in(Singleton.class);
		bind(UsuarioMongoDao.class).to(new TypeLiteral<Dao<Usuario, FiltroUsuario>>() {
		}).in(Singleton.class);
	}
}
