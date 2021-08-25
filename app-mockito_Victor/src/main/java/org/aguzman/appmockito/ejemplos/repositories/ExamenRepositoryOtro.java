package org.aguzman.appmockito.ejemplos.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.aguzman.appmockito.ejemplos.models.Examen;

public class ExamenRepositoryOtro implements ExamenRepository{

	@Override
	public List<Examen> findAllDelRepositorio() {

		// Simulando un proceso que dura 5 segundos.
		try {
			System.out.println("esto de acá");
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Examen guardar(Examen examen) {
		// TODO Auto-generated method stub
		return null;
	}

}
