package org.victorttt.junit_app.ejemplo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {

	// A un banco le pertenencen muchas cuentas, por lo tanto una lista de la clase
	// cuenta
	private List<Cuenta> cuentas;
	private String nombre;

	public Banco() {
		// En el constructor instanciamos la lista de cuentas para que cada vez que 
		// se crea un objeto haya una lista destinada a las cuentas.
		cuentas = new ArrayList<Cuenta>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void addCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
		cuenta.setBanco(this); 
	}

	// Primero se ha escrito la prueba y luego el método
	public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto) {

		// LLamamos al método que resta cantidad aplicado al objeto origen
		origen.debito(monto);// Retira

		// Lo mismo pero al revés
		destino.credito(monto);// Ingersa

	}

}
