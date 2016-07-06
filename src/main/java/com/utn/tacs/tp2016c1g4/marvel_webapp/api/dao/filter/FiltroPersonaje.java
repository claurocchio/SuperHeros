package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Personaje;

public class FiltroPersonaje implements SearchFilter<Personaje> {

	public static enum Tipo {
		NOMBRE, ID, IDS
	}

	private Object value;
	private FiltroPersonaje.Tipo tipo;

	public FiltroPersonaje(Tipo tipo, Object value) {
		super();
		this.value = value;
		this.tipo = tipo;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public FiltroPersonaje.Tipo getTipo() {
		return tipo;
	}

	public void setTipo(FiltroPersonaje.Tipo tipo) {
		this.tipo = tipo;
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
		FiltroPersonaje other = (FiltroPersonaje) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FiltroPersonaje [value=" + value + ", tipo=" + tipo + "]";
	}

	public static class Builder {
		private Collection<Long> ids;
		private Long id;
		private String nombre;

		public Builder setIds(Collection<Long> ids) {
			this.ids = new HashSet<Long>();
			for (Long id : ids) {
				if (id > 0) {
					this.ids.add(id);
				}
			}

			return this;
		}

		public Builder setIds(Long... ids) {
			this.ids = new HashSet<Long>();
			for (Long id : ids) {
				this.ids.add(id);
			}

			return this;
		}

		public void clear() {
			this.id = null;
			this.nombre = null;
			this.ids = null;
		}

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setNombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public Collection<FiltroPersonaje> build() {
			Collection<FiltroPersonaje> col = new ArrayList<FiltroPersonaje>();

			if (this.id != null) {
				col.add(new FiltroPersonaje(FiltroPersonaje.Tipo.ID, this.id));
			}

			if (this.ids != null) {
				col.add(new FiltroPersonaje(FiltroPersonaje.Tipo.IDS, this.ids));
			}

			if (this.nombre != null) {
				col.add(new FiltroPersonaje(FiltroPersonaje.Tipo.NOMBRE, this.nombre));
			}

			return col;
		}

	}

}
