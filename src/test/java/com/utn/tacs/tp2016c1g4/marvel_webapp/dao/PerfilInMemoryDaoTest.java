package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

public class PerfilInMemoryDaoTest {

	private PerfilInMemoryDao dao;
	private FiltroPerfil.Builder filterBuilder;

	public PerfilInMemoryDaoTest() {
		dao = new PerfilInMemoryDao();
		filterBuilder = new FiltroPerfil.Builder();
	}

	@Before
	public void before() {
		dao.clear();
	}

	@Test
	public void basicTest() {
		Set<Perfil> perfiles = dao.getAll();

		assertEquals(0, perfiles.size());

		Perfil p1 = new Perfil();
		p1.setUsername("pepito1");

		Perfil p2 = new Perfil();
		p2.setUsername("pepito2");

		dao.save(p1);
		dao.save(p2);

		dao.save(p1);
		dao.save(p1);

		perfiles = dao.getAll();
		assertEquals(2, perfiles.size());
	}

	@Test
	public void checkFilters() {
		Perfil p1 = new Perfil();
		p1.setUsername("pepito1");

		Perfil p2 = new Perfil();
		p2.setUsername("pepito2");

		dao.save(p1);
		dao.save(p2);

		List<FiltroPerfil> filtros = new ArrayList<>();
		filtros.add(new FiltroPerfil(FiltroPerfil.Tipo.ID, 1L));

		Set<Perfil> perfiles;

		perfiles = dao.find(filtros);

		assertEquals("cantidad resultados en filtrado por id", 1, perfiles.size());
		assertEquals("objeto esperado en filtrado por id", Long.valueOf(1), perfiles.iterator().next().getId());

		filtros.clear();
		filtros.add(new FiltroPerfil(FiltroPerfil.Tipo.ID, 1L));
		filtros.add(new FiltroPerfil(FiltroPerfil.Tipo.USERNAME, "pepito2"));

		perfiles = dao.find(filtros);

		assertEquals("cantidad resultados en combinacion imposible de filtros", 0, perfiles.size());

	}

	@Test
	public void checkFilterBuilder() {

		popularBasico();

		filterBuilder.clear();
		filterBuilder.setId(1);

		Set<FiltroPerfil> filtros;
		Set<Perfil> perfiles;

		filtros = filterBuilder.build();
		perfiles = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id", 1, perfiles.size());

		filterBuilder.clear();
		filterBuilder.setUserName("pepito2");

		filtros = filterBuilder.build();
		perfiles = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id - 2", 1, perfiles.size());
		assertEquals("filtro construido con builder para filtrado por nombre", "pepito2",
				perfiles.iterator().next().getUsername());
	}

	private void popularBasico() {

		Perfil p1 = new Perfil();
		p1.setUsername("pepito1");

		Perfil p2 = new Perfil();
		p2.setUsername("pepito2");

		dao.save(p1);
		dao.save(p2);

	}
}
