package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroGrupo;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.inmemory.GrupoInMemoryDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class GrupoInMemoryDaoTest {

	private GrupoInMemoryDao dao;
	private FiltroGrupo.Builder filterBuilder;

	public GrupoInMemoryDaoTest() {
		dao = new GrupoInMemoryDao();
		filterBuilder = new FiltroGrupo.Builder();
	}

	@Before
	public void before() {
		dao.clear();
	}

	@Test
	public void basicTest() {
		Set<Grupo> grupos = dao.getAll();

		assertEquals(0, grupos.size());

		Grupo g1 = new Grupo();
		g1.setNombre("pepito1");

		Grupo g2 = new Grupo();
		g2.setNombre("pepito2");

		dao.save(g1);
		dao.save(g2);
		
		dao.save(g1);
		
		grupos = dao.getAll();
		assertEquals(2, grupos.size());

	}

	@Test
	public void checkFilters() {
		Grupo g1 = new Grupo();
		g1.setNombre("pepito1");

		Grupo g2 = new Grupo();
		g2.setNombre("pepito2");

		dao.save(g1);
		dao.save(g2);

		List<FiltroGrupo> filtros = new ArrayList<>();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.ID, 1L));

		Set<Grupo> grupos;

		grupos = dao.find(filtros);
	
		for(Grupo unGrupo : grupos){
			assertEquals("iddddsssss", new Long(1), unGrupo.getId());
		}

		assertEquals("cantidad resultados en filtrado por id", 1, grupos.size());
		assertEquals("objeto esperado en filtrado por id", Long.valueOf(1), grupos.iterator().next().getId());

		filtros.clear();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.ID, 1L));
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.NAME, "pepito2"));

		grupos = dao.find(filtros);

		assertEquals("cantidad resultados en combinacion imposible de filtros", 0, grupos.size());
		
		filtros.clear();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.NAME, "pepito2"));
		grupos = dao.find(filtros);
		assertEquals(1, grupos.size());

	}

	@Test
	public void checkFilterBuilder() {

		popularBasico();

		filterBuilder.clear();
		filterBuilder.setId(1);

		Set<FiltroGrupo> filtros;
		Set<Grupo> grupos;

		filtros = filterBuilder.build();
		grupos = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id", 1, grupos.size());

		filterBuilder.clear();
		filterBuilder.setName("pepito2");

		filtros = filterBuilder.build();
		grupos = dao.find(filtros);
		
		assertEquals("filtro construido con builder para filtrado por id - 2", 1, grupos.size());
		assertEquals("filtro construido con builder para filtrado por nombre", "pepito2",
				grupos.iterator().next().getNombre());
	}
	
	@Test
	public void deleteTest() {
		popularBasico();
		
		Set<Grupo> grupos = dao.getAll();
		assertEquals(2, grupos.size());
		
		List<FiltroGrupo> filtros = new ArrayList<>();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.ID, 1L));
		
		grupos = dao.find(filtros);
		assertEquals(1, grupos.size());
		
		Grupo miGrupo = dao.findOne(filtros);
		assertEquals(new Long(1), miGrupo.getId());
		assertEquals("pepito1", miGrupo.getNombre());
		
		boolean seBorro = dao.delete(miGrupo);
		assertEquals(true, seBorro);
		
		grupos = dao.getAll();
		assertEquals(1, grupos.size());
		
		filtros.clear();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.NAME, "pepito2"));
		
		grupos = dao.find(filtros);
		assertEquals(1, grupos.size());
		
		miGrupo = dao.findOne(filtros);
		
		assertEquals(new Long(2), miGrupo.getId());
		assertEquals("pepito2", miGrupo.getNombre());
		
		
		seBorro = dao.delete(miGrupo);
		assertEquals(true, seBorro);

		grupos = dao.getAll();
		assertEquals(0, grupos.size());
	}

	private void popularBasico() {

		Grupo g1 = new Grupo();
		g1.setNombre("pepito1");

		Grupo g2 = new Grupo();
		g2.setNombre("pepito2");

		dao.save(g1);
		dao.save(g2);

	}
	
	@Test
	public void cloningTest() {
		Grupo g1 = new Grupo();
		g1.setNombre("pepito1");

		Grupo g2 = new Grupo();
		g2.setNombre("pepito2");

		dao.save(g1);
		dao.save(g2);

		List<FiltroGrupo> filtros = new ArrayList<>();
		filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.ID, 1L));

		Grupo miGrupo = dao.findOne(filtros);
		/*try{
			miGrupo = dao.findOne(filtros);	
		}catch(ManyResultsException e){
			Collection<Grupo> resultados = e.getResults(Grupo.class);
			assertEquals(2, resultados.size());
			
			for(Grupo unGrupo : resultados){
				assertEquals("iddddsssss", new Long(1), unGrupo.getId());
			}
		}*/
		
		
		assertEquals(new Long(1), miGrupo.getId());
		assertEquals("pepito1", miGrupo.getNombre());
		
	}
	
}
