package org.aguzman.appmockito.ejemplos.services;

import java.util.Optional;

import org.aguzman.appmockito.ejemplos.models.Examen;

/*Este es nuestro sujeto de prueba
 * aquí probamos el código real, lo demas es simular la obtención de los datos*/
public interface ExamenService {
	Optional<Examen> findExamenPorNombre(String nombre);

	Examen buscaExamenPorNombreConPreguntas(String Nombre);

	Examen guardar(Examen examen);

}
