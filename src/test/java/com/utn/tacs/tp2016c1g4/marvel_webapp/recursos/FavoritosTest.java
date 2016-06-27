package com.utn.tacs.tp2016c1g4.marvel_webapp.recursos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.FavoritoResource;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.favorito.FavoritoPutRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.favorito.FavoritoPutResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.perfil.PerfilGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyResourceConfig;
import com.utn.tacs.tp2016c1g4.marvel_webapp.hk2.MyTestResourceConfig;

import static org.junit.Assert.*;

public class FavoritosTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new MyTestResourceConfig();
	}

	@Test
	public void testPut() {
		Response response = null;
		
		UsuarioPostRequest req = new UsuarioPostRequest();
		req.setEmail("user1@mail.com");
		req.setPass("asd123");
		req.setUserName("user1");
		
		response = target("/usuarios").request().post(Entity.json(req), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		//usuario creado, agrego favorito
		FavoritoPutRequest putReq = new FavoritoPutRequest();
		
		putReq.setIdPersonaje(new Long(9999999));
		
		//usuario no existente
		response = target("/favoritos/10").request().put(Entity.json(putReq), Response.class);
		assertEquals(Response.Status.NOT_FOUND, response.getStatus());//esto esta devolviendo 404 siempre
		FavoritoPutResponse putResponse = response.readEntity(FavoritoPutResponse.class);
		assertEquals("no existe el perfil solicitado", putResponse.getStatus().getMessage());
		
		//personaje no existente
		response = target("/favoritos/1").request().put(Entity.json(putReq), Response.class);
		putResponse = response.readEntity(FavoritoPutResponse.class);
		assertEquals(Response.Status.NOT_FOUND, response.getStatus());
		assertEquals("no existe el personaje solicitado", putResponse.getStatus().getMessage());
		
		//correcto
		putReq.setIdPersonaje(new Long(1));
		response = target("/favoritos/1").request().put(Entity.json(putReq), Response.class);
		putResponse = response.readEntity(FavoritoPutResponse.class);
		assertEquals(Response.Status.CREATED, response.getStatus());
	}

	@Test
	public void testFavorito() {
		Response response = null;
		
		response = target("/favoritos/2").request().get(Response.class);
		assertEquals("get favs cuando esta vacio debe ser not found", Status.NOT_FOUND.getStatusCode(),
				response.getStatus());
		
		UsuarioPostRequest req = new UsuarioPostRequest();
		req.setEmail("user1@mail.com");
		req.setPass("asd123");
		req.setUserName("user1");
		
		response = target("/usuarios").request().post(Entity.json(req), Response.class);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
		//TODO: agregar favoritos... que funcione el put
	}

	@Test
	public void testAgregarFavoritoSinRequest() {
	}

}
