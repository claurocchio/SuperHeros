package com.utn.tacs.tp2016c1g4.marvel_webapp.external;

import java.util.List;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.Page;

public interface ExternalFetcher<T> {
	List<T> fetch(Page page);
}
