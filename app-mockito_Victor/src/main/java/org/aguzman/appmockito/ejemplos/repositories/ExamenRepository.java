package org.aguzman.appmockito.ejemplos.repositories;

import java.util.List;

import org.aguzman.appmockito.ejemplos.models.Examen;

public interface ExamenRepository {
	
	/*El objetivo es hacer un mock de este método, es decir, burlar la 
	 * funcionalidad real*/
	List<Examen> findAllDelRepositorio();
	
	// método para persistir un determinado examen
	Examen guardar(Examen examen);
}
