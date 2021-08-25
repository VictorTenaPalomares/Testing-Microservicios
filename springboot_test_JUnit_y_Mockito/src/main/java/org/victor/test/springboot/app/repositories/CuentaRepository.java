package org.victor.test.springboot.app.repositories;

import java.util.List;

import org.victor.test.springboot.app.models.Cuenta;

/*Esta es la capa DAO de acceso a datos*/
/*Aquí usaremos Mocks, es decir, burlaremos su procedencia*/
public interface CuentaRepository {
	
	List<Cuenta> findAll(); // Nombre por convención SpringBoot
	
	Cuenta findById(Long id); 
	
	void update(Cuenta cuenta); // se suela utilizar save también.
	 
}
