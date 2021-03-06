package org.aguzman.appmockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyList;
// para que funcione el any
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.aguzman.appmockito.ejemplos.models.Examen;
import org.aguzman.appmockito.ejemplos.repositories.ExamenRepository;
import org.aguzman.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

	// Mock del repositorio
	@Mock
	ExamenRepository repository;
	@Mock
	PreguntaRepository preguntaRepository;

	// Inyección dentro del servicio
	@InjectMocks
	ExamenServiceImpl service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Contexto para cuendo vienen datos
	@Test
	void findExamenPorNombre() {

		// simulamos los datos que los traemoscomo constante de la clase Datos
		when(repository.findAllDelRepositorio()).thenReturn(Datos.EXAMENES);
		// 2. Invocamos el método
		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		// 3. Comprobamos que exista el objeto
		assertTrue(examen.isPresent());

		// 4. Comprobamos que contenga los valores esperados expectativa/realidad
		assertEquals(5L, examen.orElseThrow().getId());
		assertEquals("Matemáticas", examen.orElseThrow().getNombre());

	}

	// Contexto para cuando nos viene una lista vacía
	@Test
	void findExamenPorNombreListaVacia() {

		// simulamos los datos
		List<Examen> datos = Collections.emptyList();

		// Estamos simulando la obtención de datos
		when(repository.findAllDelRepositorio()).thenReturn(datos);
		// 2. Invocamos el método
		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		// 3. Comprobamos que NO exista el objeto
		assertFalse(examen.isPresent());

	}

	@Test
	void testPreguntaExamen() {

		when(repository.findAllDelRepositorio()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.buscarPreguntasPorExamenId(7L)).thenReturn(Datos.PREGUNTAS);

		// validar si se ya llamado un determinado método de nuestro mock

		Examen examen = service.buscaExamenPorNombreConPreguntas("Historia");

		assertEquals(5, examen.getPreguntas().size());
		assertEquals("aritmética", examen.getPreguntas().get(0));

		assertTrue(examen.getPreguntas().contains("trigonometría"));

	}

	@Test
	void testPreguntaExamenVeify() {

		when(repository.findAllDelRepositorio()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.buscarPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

		Examen examen = service.buscaExamenPorNombreConPreguntas("Matemáticas");

		assertEquals(5, examen.getPreguntas().size());
		assertEquals("aritmética", examen.getPreguntas().get(0));
		assertTrue(examen.getPreguntas().contains("trigonometría"));

		// validar si se ya llamado un determinado método de nuestro mock
		verify(repository).findAllDelRepositorio();
		verify(preguntaRepository).buscarPreguntasPorExamenId(5L);

	}

	// Comprobar contexto deonde no vienen datos
	@Test
	@Disabled
	void testNoExisteExamenVerify() {

		when(repository.findAllDelRepositorio()).thenReturn(Collections.emptyList());
		when(preguntaRepository.buscarPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.buscaExamenPorNombreConPreguntas("Matemáticas");
		assertNull(examen);
		// validar si se ya llamado un determinado método de nuestro mock
		verify(repository).findAllDelRepositorio();
		verify(preguntaRepository).buscarPreguntasPorExamenId(5L);

	}

	@Test
	void testGuardarExamen() {

		// Given
		Examen newExamen = Datos.EXAMEN;
		newExamen.setPreguntas(Datos.PREGUNTAS);

		// Cuando se pretenda guardar cualquier tipo de examen , entonces retorna la
		// constante de la clase datos
		// Uso de clase interna anónima Answer para incrementar id simulando id en BDD
		when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {

			Long secuencia = 8L; // nuestra secuencia parte en 8

			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {

				Examen examen = invocation.getArgument(0);
				examen.setId(secuencia++);
				return examen;

			}

		});

		// When
		// Lamamos el método guardar del service que devuelve un examen además de
		// guradarlo
		Examen examen = service.guardar(newExamen);

		// Then
		// Hacemos las comprobaciones que nos de la gana
		assertNotNull(examen.getId());
		assertEquals(8L, examen.getId());
		assertEquals("Física", examen.getNombre());

		// Verificamos que un determinado método funcione correctamente
		verify(repository).guardar(any(Examen.class));

		verify(preguntaRepository).guardarVariasPreguntas(anyList());

	}

	@Test
	void manejoException() {
		// dado un determinado escenario...
		when(repository.findAllDelRepositorio()).thenReturn(Datos.EXAMENES_ID_NULL);
		when(preguntaRepository.buscarPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);

		// cuando pase esto...
		assertThrows(IllegalArgumentException.class, () -> {
			service.buscaExamenPorNombreConPreguntas("Matemáticas");
		});

		verify(repository).findAllDelRepositorio();
		verify(preguntaRepository).buscarPreguntasPorExamenId(isNull());

	}
	
	@Test
	void testArgumentMatchers() {
		when(repository.findAllDelRepositorio()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.buscarPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		service.buscaExamenPorNombreConPreguntas("Matemáticas");

		verify(repository).findAllDelRepositorio();
		verify(preguntaRepository).buscarPreguntasPorExamenId(argThat(arg->arg.equals(5L)));	
		
		
		
		
	}
	
	
	
	
	

}
