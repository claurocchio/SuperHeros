package com.utn.tacs.tp2016c1g4.marvel_webapp.api.task;

public interface PersonajeImporter extends Runnable {
	boolean isFinalized();

	boolean isStarted();

	void setPageLimit(int limit);

	void setPageAmount(long amount);
}
