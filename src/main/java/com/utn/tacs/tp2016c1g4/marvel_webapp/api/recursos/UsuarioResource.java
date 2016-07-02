package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;


import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.usuario.UsuarioPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario.UsuarioGetResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.usuario.UsuarioPostResponse;

@Path("/usuarios")
public class UsuarioResource {

	private static final Logger logger = LogManager.getLogger(UsuarioResource.class);

	private Dao<Usuario, FiltroUsuario> usuarioDao;
	
	private Dao<Perfil, FiltroPerfil> perfilDao;

	private Properties params;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		/*List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario miUser = new Usuario(new Long(1), new Long(1), "user1", "pass1"));
		usuarios.add(new Usuario(new Long(2), new Long(2), "user2", "pass2"));*/
		
		//agrego 3 usuarios para pruebas
		/*Usuario p = new Usuario();
		p.setUserName("user1");
		p.setEmail("user1@gmail.com");
		p.setPass("pass1");
		p.setLastAccess(111);
		Perfil nuevoPerfil = new Perfil();
		nuevoPerfil.setUsername(p.getUserName());
		nuevoPerfil.setEmail(p.getEmail());
		perfilDao.save(nuevoPerfil);
		p.setIdPerfil(nuevoPerfil.getId());
		usuarioDao.save(p);
		
		Usuario p2 = new Usuario();
		p2.setUserName("user2");
		p2.setEmail("user2@gmail.com");
		p2.setPass("pass2");
		p2.setLastAccess(222);
		Perfil nuevoPerfil2 = new Perfil();
		nuevoPerfil2 = new Perfil();
		nuevoPerfil2.setUsername(p2.getUserName());
		nuevoPerfil2.setEmail(p2.getEmail());
		perfilDao.save(nuevoPerfil2);
		p2.setIdPerfil(nuevoPerfil2.getId());
		usuarioDao.save(p2);
		
		Usuario p3 = new Usuario();
		p3.setUserName("user3");
		p3.setEmail("user3@gmail.com");
		p3.setPass("pass3");
		p3.setLastAccess(333);
		Perfil nuevoPerfil3 = new Perfil();
		nuevoPerfil3 = new Perfil();
		nuevoPerfil3.setUsername(p3.getUserName());
		nuevoPerfil3.setEmail(p3.getEmail());
		perfilDao.save(nuevoPerfil3);
		p3.setIdPerfil(nuevoPerfil3.getId());
		usuarioDao.save(p3);*/
		
		
		
		FiltroUsuario.Builder filtroBuilder = new FiltroUsuario.Builder();
		filtroBuilder.clear();
		//filtros vacio
		Set<FiltroUsuario> filtros = filtroBuilder.build();
		filtros.clear();
		Set<Usuario> usuarios = usuarioDao.find(filtros);
		
		UsuarioGetResponse.Builder responseBuilder = new UsuarioGetResponse.Builder();
		responseBuilder.setUsuarios(usuarios);
		
		UsuarioGetResponse response = responseBuilder.build();

		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("username") String userName) {
		logger.debug("get invocado");

		FiltroUsuario.Builder filtroBuilder = new FiltroUsuario.Builder();
		filtroBuilder.clear();
		filtroBuilder.setUserName(userName);
		Set<FiltroUsuario> filtros = filtroBuilder.build();

		logger.debug("filtrando por: " + filtros);

		Response.Status status = null;
		OperationStatus opStatus = new OperationStatus();
		UsuarioGetResponse.Builder responseBuilder = new UsuarioGetResponse.Builder();

		Usuario Usuario = null;
		try {
			Usuario = usuarioDao.findOne(filtros);
			responseBuilder.setUsuario(Usuario);
			status = Response.Status.OK;
		} catch (ManyResultsException e) {
			// no deberia ocurrir
			logger.error("se obtuvo mas de una coincidencia de usuario");
		}

		if (Usuario == null) {
			status = Response.Status.NOT_FOUND;
			opStatus.setMessage("no existe el Usuario solicitado");
		} else {
			if (with("perfil")) {//TODO: definir necesidad
				logger.debug("with perfil especificado... buscando");
			}
		}

		opStatus.setStatusCode(status);
		responseBuilder.setOperationStatus(opStatus);

		UsuarioGetResponse entityResponse = responseBuilder.build();

		return Response.status(status).entity(entityResponse).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(UsuarioPostRequest request) {
		logger.debug("post invocado");

		Status status = null;
		String mensaje = "";

		UsuarioPostResponse postResponse = new UsuarioPostResponse();
		postResponse.setStatus(new OperationStatus());

		if (!isValidRequest(request)) {
			status = Status.BAD_REQUEST;
			mensaje = "parámetros inválidos";
			postResponse.getStatus().setStatusCode(status);
			postResponse.getStatus().setMessage(mensaje);

			return Response.status(status).entity(postResponse).build();
		}

		FiltroUsuario.Builder filtroBuilder = new FiltroUsuario.Builder();
		filtroBuilder.setUserName(request.getUserName());
		Set<FiltroUsuario> filtros = filtroBuilder.build();

		try {
			Usuario Usuario = usuarioDao.findOne(filtros);

			if (Usuario == null) {
				Usuario p = new Usuario();
				p.setUserName(request.getUserName());
				p.setEmail(request.getEmail());
				p.setPass(request.getPass());
				// TODO: definir que hacer con este campo request.getPassword();
				
				Perfil nuevoPerfil = new Perfil();
				nuevoPerfil.setUsername(p.getUserName());
				nuevoPerfil.setEmail(p.getEmail());
				boolean success = perfilDao.save(nuevoPerfil);
				
				if(success){
					p.setIdPerfil(nuevoPerfil.getId());
					
					success = usuarioDao.save(p);
					
					if (success) {
						status = Status.OK;
						mensaje = "Usuario creado exitosamente";
						logger.debug("Usuario creado: " + p.toString());
					} else {
						status = Status.INTERNAL_SERVER_ERROR;
						mensaje = "ocurrió un problema al generar el Usuario";
						logger.error("no se generó id para el nuevo Usuario: " + request.getUserName());
						
						//borro el perfil
						perfilDao.delete(nuevoPerfil); //TODO: verificar
					}
					
				} else {
					status = Status.INTERNAL_SERVER_ERROR;
					mensaje = "ocurrió un problema al generar el Perfil asociado";
					logger.error("no se generó un perfil para el nuevo Usuario: " + request.getUserName());
				}

			} else {
				// no deberia ocurrir nunca
				status = Status.CONFLICT;
				mensaje = "el username ya existe";
			}

		} catch (ManyResultsException e) {
			// no deberia ocurrir nunca
			status = Status.CONFLICT;
			mensaje = "más de una coincidencia para ese username";
		}

		postResponse.getStatus().setStatusCode(status);
		postResponse.getStatus().setMessage(mensaje);

		return Response.status(status).entity(postResponse).build();

	}

	private boolean isValidRequest(UsuarioPostRequest request) {
		boolean isValid = true;

		isValid = isValid && request.getUserName() != null && !request.getUserName().isEmpty();
		isValid = isValid && request.getPass() != null && !request.getPass().isEmpty();
		isValid = isValid && request.getEmail() != null && !request.getEmail().isEmpty();

		return isValid;
	}

	@Inject
	public void setUsuarioDao(Dao<Usuario, FiltroUsuario> usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		logger.debug("catcheando uri info en Usuarioes");

		this.params = new Properties();
		for (String key : uriInfo.getQueryParameters().keySet()) {
			this.params.setProperty(key, uriInfo.getQueryParameters().getFirst(key));
			logger.debug("query param[ " + key + " = " + this.params.getProperty(key) + " ]");
		}
	}

	private boolean with(String adicionalRequerido) {
		String with = params.getProperty("with");
		return with != null ? with.matches(".*?,?" + adicionalRequerido + ",?.*?") : false;
	}
}