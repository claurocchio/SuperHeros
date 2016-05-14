package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Perfil;

public class FiltroPerfil implements SearchFilter<Perfil> {

	private Object value;
	private FiltroPerfil.Tipo tipo;

	public FiltroPerfil(Tipo tipo, Object value) {
		super();
		this.value = value;
		this.tipo = tipo;
	}

	public static enum Tipo {
		USERNAME, ID
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public FiltroPerfil.Tipo getTipo() {
		return tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiltroPerfil other = (FiltroPerfil) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FiltroPerfil [value=" + value + ", tipo=" + tipo + "]";
	}

}
