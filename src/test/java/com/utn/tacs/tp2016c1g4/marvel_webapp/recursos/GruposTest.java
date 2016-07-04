package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.grupo.GrupoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.grupo.GrupoPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario.UsuarioPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class GruposTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testCreateGrupo() {

		GrupoPostRequest postRequest = null;
		Response response = null;

		Usuario usuario = crearUsuario("user1", "pass1", "test@test.com");
		assertNotNull("usuario creado para asignarle grupo", usuario);

		postRequest = new GrupoPostRequest();
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos con nombre faltante debe ser bad request", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		String nombreGrupo = "unGrupo";

		postRequest.setName(nombreGrupo);
		postRequest.setIdUsuario(usuario.getId());

		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos cuando esta vacio debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		GrupoPostResponse postResponse = response.readEntity(GrupoPostResponse.class);

		assertNotNull("nuevo grupo debe tener id asignado", postResponse.getIdGrupo());
		assertEquals("nuevo grupo debe ser identico al del request", nombreGrupo, postResponse.getNombre());

		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);
		assertEquals("post a grupos con nombre existente debe ser conflict ", Status.CONFLICT.getStatusCode(),
				response.getStatus());

		postResponse = response.readEntity(GrupoPostResponse.class);

		assertNull("post a grupos en conflicto debe producir id nulo", postResponse.getIdGrupo());
		assertNull("post a grupos en conflicto debe producir nombre nulo", postResponse.getNombre());
	}

	@Test
	public void testGetGrupo() {
		Response response = null;

		response = target("/grupos/2").request().get(Response.class);
		assertEquals("get grupo cuando esta vacio debe ser not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// creo un usuario al cual asignarle los grupos
		Usuario usuario = crearUsuario("user1", "pass1", "test@test.com");
		assertNotNull("usuario creado para asignarle grupo", usuario);
		
		GrupoPostRequest postRequest = new GrupoPostRequest();

		postRequest.setName("grupo1");
		postRequest.setIdUsuario(usuario.getId());

		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);

		assertEquals("grupo 1 creado ok previo a prueba de obtencion de grupos", Status.OK.getStatusCode(),
				response.getStatus());

		postRequest.setName("grupo2");
		postRequest.setIdUsuario(usuario.getId());
		response = target("/grupos").request().post(Entity.json(postRequest), Response.class);

		assertEquals("grupo 1 creado ok previo a prueba de obtencion de grupos", Status.OK.getStatusCode(),
				response.getStatus());

		// repito busqueda de grupos
		response = target("/grupos/2").request().get(Response.class);
		assertEquals("get grupo para id existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		GrupoGetResponse getResponse = response.readEntity(GrupoGetResponse.class);
		assertNotNull("get grupo response OK debe tener grupo no nulo", getResponse.getGrupo());
		assertEquals("get grupo response OK debe tener grupo con id igual al del request", 2,
				getResponse.getGrupo().getId());
		assertNotNull("get grupo response OK para grupo debe tener objeto lista de personajes",
				getResponse.getGrupo().getPersonajes());
		assertEquals("get grupo response OK para grupo nuevo no debe contener personajes", 0,
				getResponse.getGrupo().getPersonajes().size());

	}

	private Usuario crearUsuario(String userName, String password, String email) {
		// creamos un usuario al cual asignarle grupos

		Usuario usuario = null;

		UsuarioPostRequest request = new UsuarioPostRequest();
		request.setUserName(userName);
		request.setPass(password);
		request.setEmail(email);

		Response response = target("/usuarios").request().post(Entity.json(request));

		boolean creado = false;

		creado = creado || response.getStatus() == Status.OK.getStatusCode();
		creado = creado || response.getStatus() == Status.CREATED.getStatusCode();

		if (creado) {
			UsuarioPostResponse postResponse = response.readEntity(UsuarioPostResponse.class);
			usuario = new Usuario();
			usuario.setId(postResponse.getId());
			usuario.setIdPerfil(postResponse.getIdPerfil());
			usuario.setUserName(postResponse.getUserName());
			usuario.setEmail(postResponse.getEmail());
		}

		return usuario;
	}

	@Test
	public void testAddPersonaje() {

	}

}