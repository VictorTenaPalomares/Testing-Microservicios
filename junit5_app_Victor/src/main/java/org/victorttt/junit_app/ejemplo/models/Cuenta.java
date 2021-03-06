package org.victorttt.junit_app.ejemplo.models;

import java.math.BigDecimal;

import org.victorttt.junit_app.ejemplo.exceptions.DineroInsuficienteException;


public class Cuenta {

	// Una cuenta pertenence a un solo banco , un solo atributo
	private Banco banco;
	private String persona;
	private BigDecimal saldo;

	public Cuenta(String persona, BigDecimal saldo) {

		this.saldo = saldo;
		this.persona = persona;
		

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
	
	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	// Desarrollo posterior a la escritura de una prueba unitaria fallida
	public void debito(BigDecimal monto) {
		
		
		BigDecimal nuevoSaldo=this.saldo.subtract(monto);
		
		if (nuevoSaldo.compareTo(BigDecimal.ZERO)<0 ) {
			throw new DineroInsuficienteException("Dinero Insuficiente");
			
		}
		
		this.saldo=nuevoSaldo; 
		
	}
	
	// Desarrollo posterior a la escritura de una prueba unitaria fallida
	public void credito(BigDecimal monto) {
		
		this.saldo=this.saldo.add(monto);
		
	}

	
	
	

	// Metodo que comprueba igualdad sobreescrito
	@Override
	public boolean equals(Object obj) {

		// Validamos que el objeto que recibimos por parametro no sea null
		// y además que sea de tipo cuenta
		if (!(obj instanceof Cuenta)) {
			return false;
		}

		// Hacemos un cast a nuestro objeto
		Cuenta c = (Cuenta) obj;

		// Validamos que persona y saldo sean distintos de null
		if (this.persona == null || this.saldo == null) {
			return false;
		}

		// Una vez hechas las validaciones comparamos el atributo de esta clase con el
		// que
		// recibimos por parámetro de la otra clase
		return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
	}

}
