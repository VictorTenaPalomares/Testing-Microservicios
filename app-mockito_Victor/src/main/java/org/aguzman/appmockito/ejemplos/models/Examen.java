package org.aguzman.appmockito.ejemplos.models;

import java.util.ArrayList;
import java.util.List;

public class Examen {

	private Long id;
	private String nombre;
	private List<String> preguntas;

	public Examen(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		
		// Inicializamos en el constructor el array list porque si no va a ser null
		this.preguntas = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

}