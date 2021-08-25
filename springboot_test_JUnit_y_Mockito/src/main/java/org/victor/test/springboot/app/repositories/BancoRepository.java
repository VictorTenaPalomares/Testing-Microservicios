package org.victor.test.springboot.app.repositories;

import java.util.List;

import org.victor.test.springboot.app.models.Banco;

/*Esta es la capa DAO de acceso a datos*/
/*Aquí usaremos Mocks, es decir, burlaremos su procedencia*/
public interface BancoRepository {

	List<Banco>findAll();
	
	Banco findById(Long id);
	
	void update (Banco banco);
	
}
