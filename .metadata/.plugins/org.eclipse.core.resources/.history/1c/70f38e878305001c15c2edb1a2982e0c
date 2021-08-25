package org.victor.test.springboot.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.victor.test.springboot.app.models.Banco;
import org.victor.test.springboot.app.models.Cuenta;
import org.victor.test.springboot.app.repositories.BancoRepository;
import org.victor.test.springboot.app.repositories.CuentaRepository;


@Service
public class CuentaServiceImpl implements CuentaService {

	/*
	 * La capa de servicio siempre trabaja, opera con otros objetos que vienen de la
	 * capa dao
	 */
	/* Esos objetos tienen que ser inyectados como dependencias */
	private CuentaRepository cuentaRepository;
	private BancoRepository bancoRepository;

	// Constructores para que Mockito pueda inyectarlos
	public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
		this.cuentaRepository = cuentaRepository;
		this.bancoRepository = bancoRepository;
	}

	public CuentaServiceImpl() {
	}

	@Override
	public Cuenta findById(Long id) {
		return cuentaRepository.findById(id);
	}

	@Override
	public int revisarTotalTransferencias(Long bancoId) {
		Banco banco = bancoRepository.findById(bancoId);
		return banco.getTotalTransferencias();

	}

	@Override
	public BigDecimal revisarSaldo(Long cuentaId) {
		Cuenta cuenta = cuentaRepository.findById(cuentaId);
		return cuenta.getSaldo();
	}

	@Override
	public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

		
		

		Cuenta cuenta1 = cuentaRepository.findById(numCuentaOrigen);
		cuenta1.debito(monto);
		cuentaRepository.update(cuenta1);

		Cuenta cuenta2 = cuentaRepository.findById(numCuentaDestino);
		cuenta2.credito(monto);
		cuentaRepository.update(cuenta2);
		
		Banco banco = bancoRepository.findById(bancoId);
		int totalTransferencias = banco.getTotalTransferencias(); // acceso a transferencias
		banco.setTotalTransferencias(++totalTransferencias); // preincrementamos
		bancoRepository.update(banco);

	}

}
