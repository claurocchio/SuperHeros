package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.login.LoginPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.login.LoginPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

public class LoginTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testLogin() {

		Response response = null;

		UsuarioPostRequest usuarioPostRequest = new UsuarioPostRequest();

		usuarioPostRequest.setUserName("elUsuario");
		usuarioPostRequest.setPass("laContraseña");
		usuarioPostRequest.setEmail("test@test.com");

		response = target("/usuarios").request().post(Entity.json(usuarioPostRequest), Response.class);

		LoginPostRequest loginPostRequest = new LoginPostRequest();
		LoginPostResponse loginPostResponse = new LoginPostResponse();

		response = target("/login").request().post(Entity.json(loginPostRequest), Response.class);
		loginPostResponse = response.readEntity(LoginPostResponse.class);
		assertEquals("parámetros inválidos", loginPostResponse.getStatus().getMessage());
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		loginPostRequest.setUserName("usuarioTrucho");
		loginPostRequest.setPass("cualquierPassword");

		response = target("/login").request().post(Entity.json(loginPostRequest), Response.class);
		loginPostResponse = response.readEntity(LoginPostResponse.class);
		assertEquals("el usuario no existe", loginPostResponse.getStatus().getMessage());
		assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());

		loginPostRequest.setUserName("elUsuario");
		loginPostRequest.setPass("cualquierPassword");

		response = target("/login").request().post(Entity.json(loginPostRequest), Response.class);
		loginPostResponse = response.readEntity(LoginPostResponse.class);
		assertEquals("la contraseña es incorrecta", loginPostResponse.getStatus().getMessage());
		assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());

		loginPostRequest.setPass("laContraseña");

		response = target("/login").request().post(Entity.json(loginPostRequest), Response.class);
		loginPostResponse = response.readEntity(LoginPostResponse.class);
		assertEquals("login correcto", loginPostResponse.getStatus().getMessage());
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		assertNotNull(loginPostResponse.getToken());
		assertNotNull(loginPostResponse.getIdUsuario());
	}
}
