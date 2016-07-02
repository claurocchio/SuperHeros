package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Dao;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPerfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroUsuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.request.login.LoginPostRequest;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.login.LoginPostResponse;
import com.utn.tacs.tp2016c1g4.marvel_webapp.utils.Md5Sum;

@Path("/login")
public class LoginResource {

	private static final Logger logger = LogManager.getLogger(LoginResource.class);

	private Dao<Usuario, FiltroUsuario> usuarioDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(LoginPostRequest request) {
		logger.debug("post invocado");

		Status status = null;
		String mensaje = "";
		LoginPostResponse postResponse = new LoginPostResponse();
		postResponse.setStatus(new OperationStatus());

		if (!isValidRequest(request)) {
			status = Status.BAD_REQUEST;
			mensaje = "parámetros inválidos";
			postResponse.getStatus().setStatusCode(status);
			postResponse.getStatus().setMessage(mensaje);

			return Response.status(status).entity(postResponse).build();
		}

		FiltroUsuario.Builder filtroBuilder = new FiltroUsuario.Builder();
		filtroBuilder.clear();
		filtroBuilder.setUserName(request.getUserName());
		Set<FiltroUsuario> filtros = filtroBuilder.build();
		Usuario usuario = usuarioDao.findOne(filtros);

		if (usuario == null) {
			status = Status.CONFLICT;
			mensaje = "el usuario no existe";
		} else if (!usuario.getPass().equals(Md5Sum.getMD5(request.getUserName() + request.getPass()))) {
			status = Status.CONFLICT;
			mensaje = "la contraseña es incorrecta";
		} else {
			status = Status.OK;
			mensaje = "login correcto";
			String token = Md5Sum.getMD5(Long.toString(System.currentTimeMillis()));
			postResponse.setIdUsuario(usuario.getId());
			postResponse.setToken(token);
		}

		logger.debug(mensaje + ": " + request.getUserName());
		postResponse.getStatus().setStatusCode(status);
		postResponse.getStatus().setMessage(mensaje);

		return Response.status(status).entity(postResponse).build();
	}

	@Inject
	public void setUsuarioDao(Dao<Usuario, FiltroUsuario> usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	private boolean isValidRequest(LoginPostRequest request) {
		boolean isValid = true;

		isValid = isValid && request.getUserName() != null && !request.getUserName().isEmpty();
		isValid = isValid && request.getPass() != null && !request.getPass().isEmpty();

		return isValid;
	}

}
