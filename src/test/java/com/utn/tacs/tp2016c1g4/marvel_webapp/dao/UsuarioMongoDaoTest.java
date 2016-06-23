package com.utn.tacs.tp2016c1g4.marvel_webapp.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.mongo.UsuarioMongoDao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

public class UsuarioMongoDaoTest {

	private UsuarioMongoDao dao;
	private FiltroUsuario.Builder filterBuilder;

	public UsuarioMongoDaoTest() {
		dao = new UsuarioMongoDao();
		filterBuilder = new FiltroUsuario.Builder();
	}

	@Before
	public void before() {
		dao.clear();
	}

	@Test
	public void basicTest() {
		Set<Usuario> usuarios = dao.getAll();

		assertEquals(0, usuarios.size());

		Usuario p1 = new Usuario();
		p1.setUserName("pepito1");

		Usuario p2 = new Usuario();
		p2.setUserName("pepito2");

		dao.save(p1);
		dao.save(p2);

		dao.save(p1);
		dao.save(p1);

		usuarios = dao.getAll();
		assertEquals(2, usuarios.size());
	}

	@Test
	public void checkFilters() {
		Usuario p1 = new Usuario();
		p1.setUserName("pepito1");
		p1.setEmail("pepito1@gmail.com");
		p1.setIdPerfil(new Long(1));
		p1.setPass("pass1");

		Usuario p2 = new Usuario();
		p2.setUserName("pepito2");
		p2.setEmail("pepito2@gmail.com");
		p2.setIdPerfil(new Long(2));
		p2.setPass("pass2");

		dao.save(p1);
		dao.save(p2);

		List<FiltroUsuario> filtros = new ArrayList<>();
		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.ID, 1L));

		Set<Usuario> usuarios;

		usuarios = dao.find(filtros);

		assertEquals("cantidad resultados en filtrado por id", 1, usuarios.size());
		assertEquals("objeto esperado en filtrado por id", Long.valueOf(1), usuarios.iterator().next().getId());

		filtros.clear();
		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.ID, 1L));
		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.USERNAME, "pepito2"));

		usuarios = dao.find(filtros);

		assertEquals("cantidad resultados en combinacion imposible de filtros", 0, usuarios.size());

		filtros.clear();
		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.USERNAME, "pepito2"));
		Usuario miUser = dao.findOne(filtros);

		assertEquals("pepito2", miUser.getUserName());

		filtros.clear();
		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.IDPERFIL, 1L));
		usuarios = dao.find(filtros);

		assertEquals(1, usuarios.size());

		filtros.clear();
		Collection<Long> ids = new ArrayList<>();
		ids.add(new Long(1));
		ids.add(new Long(2));
		ids.add(new Long(3));

		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.IDS, ids));
		usuarios = dao.find(filtros);

		assertEquals(2, usuarios.size());

		filtros.clear();
		ids.clear();
		ids.add(new Long(4));
		ids.add(new Long(3));

		filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.IDS, ids));
		usuarios = dao.find(filtros);

		assertEquals(0, usuarios.size());

		// no filters check
		filtros.clear();
		usuarios = dao.find(filtros);

		assertEquals(2, usuarios.size());
	}

	@Test
	public void checkFilterBuilder() {

		popularBasico();

		filterBuilder.clear();
		filterBuilder.setId((long) 1);

		Set<FiltroUsuario> filtros;
		Set<Usuario> usuarios;

		filtros = filterBuilder.build();
		usuarios = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id", 1, usuarios.size());

		filterBuilder.clear();
		filterBuilder.setUserName("pepito2");

		filtros = filterBuilder.build();
		usuarios = dao.find(filtros);

		assertEquals("filtro construido con builder para filtrado por id - 2", 1, usuarios.size());
		assertEquals("filtro construido con builder para filtrado por nombre", "pepito2",
				usuarios.iterator().next().getUserName());
	}

	private void popularBasico() {

		Usuario p1 = new Usuario();
		p1.setUserName("pepito1");
		p1.setEmail("pepito1@gmail.com");
		p1.setIdPerfil(new Long(1));
		p1.setPass("pass1");

		Usuario p2 = new Usuario();
		p2.setUserName("pepito2");
		p2.setEmail("pepito2@gmail.com");
		p2.setIdPerfil(new Long(2));
		p2.setPass("pass2");

		dao.save(p1);
		dao.save(p2);

	}
}
