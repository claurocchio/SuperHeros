package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PerfilInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.PersonajeInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;

public class PersonajesInMemoryDaoTest {

	private PersonajeInMemoryDao dao;

	private FiltroPersonaje.Builder filterBuilder;

	public PersonajesInMemoryDaoTest() {
		dao = new PersonajeInMemoryDao();
		filterBuilder = new FiltroPersonaje.Builder();
	}

	@Before
	public void before() {
		dao.clear();
	}

	@Test
	public void basicTest() {
		Set<Personaje> personajes = dao.getAll();

		assertEquals(0, personajes.size());

		popularBasico();

		personajes = dao.getAll();
		assertEquals(2, personajes.size());
	}

	@Test
	public void checkFilters() {

		popularBasico();

		List<FiltroPersonaje> filtros = new ArrayList<>();
		filtros.add(new FiltroPersonaje(FiltroPersonaje.Tipo.ID, 1L));

		Set<Personaje> personajes;

		personajes = dao.find(filtros);

		assertEquals("cantidad resultados en filtrado por id", 1, personajes.size());
		assertEquals("objeto esperado en filtrado por id", Long.valueOf(1), personajes.iterator().next().getId());

		filtros.clear();
		filtros.add(new FiltroPersonaje(FiltroPersonaje.Tipo.ID, 1L));
		filtros.add(new FiltroPersonaje(FiltroPersonaje.Tipo.NOMBRE, "batman"));

		personajes = dao.find(filtros);

		assertEquals("cantidad resultados en combinacion imposible de filtros", 0, personajes.size());

	}

	@Test
	public void checkFilterBuilder() {

		popularBasico();

		filterBuilder.clear();
		filterBuilder.setId(1L);

		Collection<FiltroPersonaje> filtros;
		Set<Personaje> personajes;

		filtros = filterBuilder.build();
		personajes = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id", 1, personajes.size());

		filterBuilder.clear();
		filterBuilder.setNombre("thor");

		filtros = filterBuilder.build();
		personajes = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id - 2", 1, personajes.size());
		assertEquals("filtro construido con builder para filtrado por nombre", "thor",
				personajes.iterator().next().getNombre());
	}

	private void popularBasico() {

		Personaje p1 = new Personaje();
		p1.setNombre("hulk");

		Personaje p2 = new Personaje();
		p2.setNombre("thor");

		dao.save(p1);
		dao.save(p2);
	}
}
