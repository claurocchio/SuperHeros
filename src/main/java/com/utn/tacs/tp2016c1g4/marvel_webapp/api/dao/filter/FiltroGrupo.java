package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.HashSet;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Grupo;

public class FiltroGrupo implements SearchFilter<Grupo>{
	private Object value;
	private FiltroGrupo.Tipo tipo;

	public FiltroGrupo(Tipo tipo, Object value) {
		super();
		this.value = value;
		this.tipo = tipo;
	}

	public static enum Tipo {
		NAME, ID
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public FiltroGrupo.Tipo getTipo() {
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
		FiltroGrupo other = (FiltroGrupo) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FiltroGrupo [value=" + value + ", tipo=" + tipo + "]";
	}

	public static class Builder {

		private Long id;
		private String name;

		public void clear() {
			this.id = null;
			this.name = null;
		}

		public Set<FiltroGrupo> build() {

			Set<FiltroGrupo> filtros = new HashSet<>();

			if (this.id != null)
				filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.ID, id));

			if (this.name != null)
				filtros.add(new FiltroGrupo(FiltroGrupo.Tipo.NAME, name));

			return filtros;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setId(String id) {
			this.id = Long.parseLong(id);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
