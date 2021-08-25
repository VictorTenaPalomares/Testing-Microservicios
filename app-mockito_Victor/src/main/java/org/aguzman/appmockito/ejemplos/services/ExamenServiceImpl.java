package org.aguzman.appmockito.ejemplos.services;

import java.util.List;
import java.util.Optional;

import org.aguzman.appmockito.ejemplos.models.Examen;
import org.aguzman.appmockito.ejemplos.repositories.ExamenRepository;
import org.aguzman.appmockito.ejemplos.repositories.PreguntaRepository;

public class ExamenServiceImpl implements ExamenService {

	// Interactuamos con el repositorio, trayéndonos las dependencias como atributos
	private ExamenRepository examenRepository;
	private PreguntaRepository preguntaRepository;

	// Constructor para asignar las dependencias
	public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
		this.examenRepository = examenRepository;
		this.preguntaRepository = preguntaRepository;
	}

	@Override
	public Optional<Examen> findExamenPorNombre(String nombre) {

		// 1.Usamos el repositorio que es una lista y la recorremos con la API stream
		// 2. aplicamos filter devuelve un predicate y aplicamos lambda
		// 3. emitimos un parámetro(e) que representará un examen
		// 4. a partir de la lambda hacemos algo con él, en este caso filtrar por nombre
		// 5. obtenemos el nombre buscado en un stream y lo pasamos a uno solo
		// con findFirst que devuelve un optional por lo tanto en este punto
		// agregamos Optional al prncipio y lo igualamos a examenRepository
		// En resumen tendremos una representación en Optional del objeto
		// buscado que además nos evitará un posible null pointer.
		// Acto seguido del findfirst,

		return examenRepository.findAllDelRepositorio().stream().filter(e -> e.getNombre().contains(nombre))
				.findFirst();

	}

	@Override
	public Examen buscaExamenPorNombreConPreguntas(String nombre) {

		// Obtenemos el examen para a partir de él encontrar las preguntas
		Optional<Examen> examenOptional = findExamenPorNombre(nombre);

		// Si se ha obtenido algún examen buscamos las preguntas en otro repositorio
		// simulando que los datos están en fuentes distintas
		Examen examen = null; // para poder acceder al id

		if (examenOptional.isPresent()) {
			examen = examenOptional.orElseThrow(); // es mejor que .get()
			List<String> preguntas = preguntaRepository.buscarPreguntasPorExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}

		return examen;
	}

	@Override
	public Examen guardar(Examen examen) {

		// Comprobamos contenido
		if (!examen.getPreguntas().isEmpty()) {
			// primero guardamos las preguntas mediante el repositorio de preguntas
			preguntaRepository.guardarVariasPreguntas(examen.getPreguntas());
		}

		// por último guradamos el mismo examen que devolvemos 
		return examenRepository.guardar(examen);
	}

}
