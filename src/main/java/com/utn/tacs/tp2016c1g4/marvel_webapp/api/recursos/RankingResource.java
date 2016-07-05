package com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter.FiltroPersonaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.OperationStatus;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.response.ranking.RankingGetResponse;

@Path("/ranking")
public class RankingResource {

	private static final Logger logger = LogManager.getLogger(PerfilResource.class);

	private Dao<Perfil, FiltroPerfil> perfilDao;
	private Dao<Personaje, FiltroPersonaje> personajeDao;

	private Properties params;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		logger.debug("get invocado");

		FiltroPerfil.Builder filtroBuilder = new FiltroPerfil.Builder();
		filtroBuilder.clear();
		//filtros vacios = devuelve todos
		Set<FiltroPerfil> filtros = filtroBuilder.build();
		filtros.clear();
		//rankingGetResponse!!
		Set<Perfil> perfiles = perfilDao.find(filtros);
		
		List<Long> temp = new ArrayList<>();
		List<Long> ranking = new ArrayList<>();
        Map<Long,Integer> m = new HashMap<>();
        
        //agrego todos los favs en una lista temporal
		for(Perfil p : perfiles){
			temp.addAll(p.getIdsPersonajesFavoritos());
		}
		
		//lleno el map
		for(Long i : temp){
            if(m.containsKey(i)){
                m.put(i, m.get(i)+1);
            }else{
                m.put(i, 1);
            }
        }
		
		//lleno el ranking de a uno
		for (int i = 0; i < 5; i++) {
			Long ans = 0L;
			int maxVal = 0;
		    for(Long in: m.keySet()){
		    	if(!ranking.contains(in) && m.get(in) >= maxVal){
		    		ans=in;
		        	maxVal = m.get(in);
		    	}
		    }
		    ranking.add(ans);
		}
		
	/*	ranking.add("Hulk");
		ranking.add("Spiderman");
		ranking.add("Thor");*/
		
		RankingGetResponse.Builder responseBuilder = new RankingGetResponse.Builder();
		responseBuilder.setPersonajeDao(personajeDao);
		responseBuilder.setPersonajes(ranking);
		
		RankingGetResponse response = responseBuilder.build();
		return Response.status(200).entity(response).build();
	}

	@Inject
	public void setPerfilDao(Dao<Perfil, FiltroPerfil> perfilDao) {
		this.perfilDao = perfilDao;
	}
	
	@Inject
	public void setPersonajeDao(Dao<Personaje, FiltroPersonaje> personajeDao) {
		this.personajeDao = personajeDao;
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		logger.debug("catcheando uri info en perfiles");

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
