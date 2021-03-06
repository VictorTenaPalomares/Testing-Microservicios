package org.victor.test.springboot.app.services;

import java.math.BigDecimal;

import org.victor.test.springboot.app.models.Cuenta;
//dependerá de repositorio Cuenta que será Mock
/*en esta capa implementamos la lógica de negocio*/
public interface CuentaService {
	
Cuenta findById(Long id); 

int revisarTotalTransferencias(Long bancoId); 

BigDecimal revisarSaldo(Long cuentaId); 

void transferir(Long numCuentaOrigen,Long numCuentaDestino, BigDecimal monto,
		Long bancoId);



}
