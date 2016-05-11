package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.SearchFilterBuilder;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.InMemoryGrupoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class InMemoryGrupoDaoTest {

	private InMemoryGrupoDao dao;
	private SearchFilterBuilder filterBuilder;

	public InMemoryGrupoDaoTest() {
		this.dao = new InMemoryGrupoDao();
		this.filterBuilder = new SearchFilterBuilder();
	}

	@Test
	public void testAdd() {
		InMemoryGrupoDao dao = this.dao;
		dao.clear();
		Set<Grupo> grupos = dao.getAll();
		assertEquals(grupos.size(), 0);

		String nombreGrupo = "LALA";
		Grupo grupo = new Grupo();
		grupo.setNombre(nombreGrupo);

		dao.add(grupo);

		grupos = dao.getAll();
		assertEquals(grupos.size(), 1);
		assertEquals(grupos.iterator().next().getId(), Long.valueOf(1L));
	}

	@Test
	public void testFindWithFilters() {
		InMemoryGrupoDao dao = this.dao;
		dao.clear();

		String nombreGrupo1 = "LALA";
		String nombreGrupo2 = "LALA2";

		Grupo grupo1 = new Grupo();
		grupo1.setNombre(nombreGrupo1);

		Grupo grupo2 = new Grupo();
		grupo2.setNombre(nombreGrupo2);

		dao.add(grupo1);
		dao.add(grupo2);

		Set<Grupo> grupos = dao.getAll();

		assertEquals(grupos.size(), 2);

		filterBuilder.clear();
		filterBuilder.include("nombre", nombreGrupo2);
		List<SearchFilter> filters = filterBuilder.build();
		grupos = dao.find(filters);

		assertEquals(grupos.size(), 1);
		assertEquals(grupos.iterator().next().getId(), grupo2.getId());
	}
}
