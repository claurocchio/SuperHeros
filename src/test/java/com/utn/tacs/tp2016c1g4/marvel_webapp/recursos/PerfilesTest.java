package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.PerfilResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.perfil.PerfilPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.login.LoginPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario.UsuarioPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

public class PerfilesTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testPerfilesGet() {
		Response response = null;
		response = target("/perfiles/1").request().get(Response.class);
		assertEquals("el perfil aun no fue creado y debe tirar not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

		// creo un usuario/perfil

		UsuarioPostRequest usuarioPostRequest = new UsuarioPostRequest();

		usuarioPostRequest.setUserName("ejemplo");
		usuarioPostRequest.setPass("laContraseña");
		usuarioPostRequest.setEmail("test@test.com");

		response = target("/usuarios").request().post(Entity.json(usuarioPostRequest), Response.class);
		UsuarioPostResponse usuarioPostResponse = response.readEntity(UsuarioPostResponse.class);
		assertNotNull("debe crearse el usuario", usuarioPostResponse.getId());
		assertNotNull("debe crearse el perfil al crear el usuario", usuarioPostResponse.getIdPerfil());

		response = target("/perfiles/" + usuarioPostResponse.getIdPerfil()).request().get(Response.class);
		assertEquals("obtencion de perfil existente debe ser ok", Status.OK.getStatusCode(), response.getStatus());

		PerfilGetResponse getResponse = response.readEntity(PerfilGetResponse.class);
		assertEquals("el nombre del perfil creado y el obtenido deben coincidir", "ejemplo",
				getResponse.getPerfil().getUsername());
		assertEquals("el email del perfil creado y el obtenido deben coincidir", "test@test.com",
				getResponse.getPerfil().getEmail());

	}

}
