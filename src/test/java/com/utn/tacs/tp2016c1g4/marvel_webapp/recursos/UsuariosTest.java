package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PersonajesResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.UsuarioResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario.UsuarioGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

public class UsuariosTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testUsuariosGet() {
		Response response = null;
		/*response = target("/api/usuarios").request().get(Response.class);
		assertEquals("No hay usuarios, debe devolver not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());*/
		
		UsuarioPostRequest postRequest = new UsuarioPostRequest();
		
		//creo user1
		postRequest.setUserName("user1");
		postRequest.setPass("123");
		postRequest.setEmail("test@test.com");
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
		//creo user2
		postRequest.setUserName("user2");
		postRequest.setPass("1234");
		postRequest.setEmail("test2@test.com");
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
		
		/*response = target("api/usuarios/user1").request().get(Response.class);
		UsuarioGetResponse getResponse1 = response.readEntity(UsuarioGetResponse.class);
		assertEquals("el nombre del usuario creado y el obtenido deben coincidir", "user1",
				getResponse1.getUsuario().getUserName());*/
		
		
		response = target("/usuarios").request().get(Response.class);
		
		/*response = target("/api/usuarios/user1").request().get(Response.class);
		assertEquals("obtencion de usuario existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());*/

		UsuarioGetResponse getResponse = response.readEntity(UsuarioGetResponse.class);
		assertEquals("cantidad de usuarios igual a 2", 2, getResponse.getUsuarios().size());
	}
	
	@Test
	public void testUsuarioGet() {
		Response response = null;
		response = target("/usuarios/ejemplo").request().get(Response.class);
		assertEquals("No hay usuarios, debe devolver not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// creo un usuario

		UsuarioPostRequest postRequest = new UsuarioPostRequest();
		postRequest.setUserName("ejemplo");
		postRequest.setPass("123");
		postRequest.setEmail("test@test.com");

		response = target("/usuarios/ejemplo").request().post(Entity.json(postRequest), Response.class);
		assertEquals("url con nombre al final debe tirar not allowed al hacer post",
				Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());

		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("/usuarios/ejemplo").request().get(Response.class);
		assertEquals("obtencion de perfil existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		UsuarioGetResponse getResponse = response.readEntity(UsuarioGetResponse.class);
		assertEquals("el nombre del usuario creado y el obtenido deben coincidir", "ejemplo",
				getResponse.getUsuario().getUserName());
		assertEquals("el email del usuario creado y el obtenido deben coincidir", "test@test.com",
				getResponse.getUsuario().getEmail());
		assertEquals("el idPerfil del usuario debe ser 1 ya que es el unico", new Long(1), getResponse.getUsuario().getIdPerfil());

	}

	@Test
	public void testUsuariosPost() {

		Response response = null;

		UsuarioPostRequest postRequest = new UsuarioPostRequest();
		postRequest.setUserName("ejemplo");
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals("bad request al no proveer contraseña al postl", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		postRequest.setPass("123");
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals("bad request al no proveer email al post", Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());

		postRequest.setEmail("test@test.com");
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("/usuarios/ejemplo").request().get(Response.class);

		UsuarioGetResponse usuarioGet = response.readEntity(UsuarioGetResponse.class);
		assertNotNull("recien cargado, no puede ser null", usuarioGet.getUsuario());
		
		response = target("/usuarios").request().post(Entity.json(postRequest), Response.class);
		assertEquals("usuarios con el mismo username tienen que ser conflict", Status.CONFLICT.getStatusCode(),
				response.getStatus());

	}
}
