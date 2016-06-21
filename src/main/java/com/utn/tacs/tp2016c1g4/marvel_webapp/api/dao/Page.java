package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

public class Page {
	private int page;
	private int limit;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "Page [page=" + page + ", limit=" + limit + "]";
	}

}
