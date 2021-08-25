package org.aguzman.appmockito.ejemplos.repositories;

import java.util.List;

public interface PreguntaRepository {

	List<String> buscarPreguntasPorExamenId(Long id);

	// método para persistir una determinada pregunta
	void guardarVariasPreguntas(List<String> preguntas);
}
