package com.utn.tacs.tp2016c1g4.marvel_webapp.mock;

import java.util.ArrayList;
import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.ExternalFetcher;
import com.utn.tacs.tp2016c1g4.marvel_webapp.external.domain.PersonajeMarvel;

public class EmptyPersonajeFetcher implements ExternalFetcher<PersonajeMarvel> {

	@Override
	public List<PersonajeMarvel> fetch(Page page) {
		return new ArrayList<PersonajeMarvel>();
	}

}
