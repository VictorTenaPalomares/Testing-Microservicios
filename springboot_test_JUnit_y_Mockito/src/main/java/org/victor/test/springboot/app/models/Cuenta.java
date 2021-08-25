package org.victor.test.springboot.app.models;

import java.math.BigDecimal;
import java.util.Objects;

import org.victor.test.springboot.app.exceptions.DineroInsuficienteException;

public class Cuenta {
	// Atributos
	/* Usar wrapper y no primitivos */
	private Long id;
	private String persona;
	private BigDecimal saldo;

	// Constructores
	public Cuenta() {
	}

	public Cuenta(Long id, String persona, BigDecimal saldo) {
		this.id = id;
		this.persona = persona;
		this.saldo = saldo;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	// Métodos especiales para validar si dos objetos son similares
	@Override
	public int hashCode() {
		return Objects.hash(id, persona, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(id, other.id) && Objects.equals(persona, other.persona)
				&& Objects.equals(saldo, other.saldo);
	}
	
	// Métodos personalizados para añadir funcionalidad a la clase
	
	// Abonar cantidad a otra cuenta
	public void debito(BigDecimal monto) {
		// Recordar que BigDecimal es inmutable, por lo tanto se hace así
		BigDecimal nuevoSaldo=this.getSaldo().subtract(monto);
		
		// Validamos que no nos vayamos a quedar sin saldo
		if (nuevoSaldo.compareTo(BigDecimal.ZERO)<0) {
			throw new DineroInsuficienteException("Dinero insuficiente en la cuenta.");
		}
		// Si todo ha ido bien asignamos
		this.saldo=nuevoSaldo;
		
	}
	
	// Recibir cantiodad de otra cuenta
	public void credito(BigDecimal monto) {
		this.saldo=this.getSaldo().add(monto);
	}

	

}
