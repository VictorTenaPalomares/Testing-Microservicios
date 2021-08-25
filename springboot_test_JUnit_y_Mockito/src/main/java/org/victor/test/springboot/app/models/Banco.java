package org.victor.test.springboot.app.models;

public class Banco {

	// Atributos
	private Long id;
	private String nombre;
	private int totalTransferencias; // primitivo para que por defecto sea 0

	// Constructores
	public Banco() {
	}

	public Banco(Long id, String nombre, int totalTransferencias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.totalTransferencias = totalTransferencias;
	}

	// Getters & Setters
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

	public int getTotalTransferencias() {
		return totalTransferencias;
	}

	public void setTotalTransferencias(int totalTransferencias) {
		this.totalTransferencias = totalTransferencias;
	}

}
