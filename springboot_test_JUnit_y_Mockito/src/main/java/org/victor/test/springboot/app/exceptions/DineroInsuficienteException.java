package org.victor.test.springboot.app.exceptions;

/*Nuestra clase de error personalizada*/
@SuppressWarnings("serial")
public class DineroInsuficienteException extends RuntimeException {
	public DineroInsuficienteException(String mensaje) {
		super(mensaje);
	}

}
