package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.InMemoryGrupoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;
import static com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FilterSugaring.*;

public class InMemoryGrupoDaoTest {

	@Test
	public void testSave() {
		InMemoryGrupoDao dao = new InMemoryGrupoDao();
		dao.clear();
		Set<Grupo> grupos = dao.getAll();
		assertEquals(grupos.size(), 0);

		String nombreGrupo = "LALA";
		Grupo grupo = new Grupo();
		grupo.setNombre(nombreGrupo);

		dao.save(grupo);

		grupos = dao.getAll();
		assertEquals(grupos.size(), 1);
		assertEquals(grupos.iterator().next().getId(), Long.valueOf(1L));
	}

	@Test
	public void testFindWithFilters() {
		InMemoryGrupoDao dao = new InMemoryGrupoDao();
		dao.clear();

		String nombreGrupo1 = "LALA";
		String nombreGrupo2 = "LALA2";

		Grupo grupo1 = new Grupo();
		grupo1.setNombre(nombreGrupo1);

		Grupo grupo2 = new Grupo();
		grupo2.setNombre(nombreGrupo2);

		dao.save(grupo1);
		dao.save(grupo2);

		Set<Grupo> grupos = dao.getAll();

		assertEquals(2, grupos.size());

		System.out.println(grupo2.getId());
		grupos = dao.find(eq("id", grupo2.getId()));

		assertEquals(1, grupos.size());
		assertEquals(grupo2.getId(), grupos.iterator().next().getId());

		grupos = dao.find(eq("nombre", ""));
		assertEquals(0, grupos.size());

		grupos = dao.find(in("nombre", nombreGrupo1, nombreGrupo2));
		assertEquals(2, grupos.size());

		// grupos = dao.find(in("nombre", null));
		// assertEquals(2, grupos.size());

	}
}
