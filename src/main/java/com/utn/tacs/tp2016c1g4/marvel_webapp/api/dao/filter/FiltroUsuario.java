package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.SearchFilter;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Usuario;

public class FiltroUsuario implements SearchFilter<Usuario> {

	public static enum Tipo {
		IDPERFIL, ID, IDS, USERNAME
	}

	private Object value;
	private FiltroUsuario.Tipo tipo;

	public FiltroUsuario(Tipo tipo, Object value) {
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

	public FiltroUsuario.Tipo getTipo() {
		return tipo;
	}

	public void setTipo(FiltroUsuario.Tipo tipo) {
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
		FiltroUsuario other = (FiltroUsuario) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FiltroUsuario [value=" + value + ", tipo=" + tipo + "]";
	}

	public static class Builder {
		private Collection<Long> ids;
		private Long id;
		private Long idPerfil;
		private String userName;

		public Builder setIds(Collection<Long> ids) {
			this.ids = ids;
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
			this.idPerfil = null;
			this.ids = null;
			this.userName = null;
		}

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setIdPerfil(Long idPerfil) {
			this.idPerfil = idPerfil;
			return this;
		}
		
		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Set<FiltroUsuario> build() {
			Set<FiltroUsuario> filtros = new HashSet<FiltroUsuario>();

			if (this.id != null) {
				filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.ID, this.id));
			}

			if (this.ids != null) {
				filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.IDS, this.ids));
			}

			if (this.idPerfil != null) {
				filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.IDPERFIL, this.idPerfil));
			}
			
			if (this.userName != null) {
				filtros.add(new FiltroUsuario(FiltroUsuario.Tipo.USERNAME, this.userName));
			}

			return filtros;
		}

	}

}
