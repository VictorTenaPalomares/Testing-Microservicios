//package org.victor.test.springboot.app;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.victor.test.springboot.app.exceptions.DineroInsuficienteException;
//import org.victor.test.springboot.app.models.Banco;
//import org.victor.test.springboot.app.models.Cuenta;
//import org.victor.test.springboot.app.repositories.BancoRepository;
//import org.victor.test.springboot.app.repositories.CuentaRepository;
//import org.victor.test.springboot.app.services.CuentaServiceImpl;
//
//@SpringBootTest
//class SpringbootTestVictorApplicationTests_ConAnotacionesMockito {
//
//	@Mock
//	CuentaRepository cuentaRepository;
//	@Mock
//	BancoRepository bancoRepository;
//
//	// Es necesario inyectar la impl (clase) porque contiene los constructores
//	// y así se llevan a cabo las inyecciones de dependencias
//	@InjectMocks
//	CuentaServiceImpl service; 
//
//	@Test
//	void contextLoads() {
//
//		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
//		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
//
//		BigDecimal saldoOrigen = service.revisarSaldo(1l);
//		BigDecimal saldoDestino = service.revisarSaldo(2L);
//
//		assertEquals("1000", saldoOrigen.toPlainString());
//		assertEquals("2000", saldoDestino.toPlainString());
//
//		service.transferir(1L, 2L, new BigDecimal("100"), 1L);
//		saldoOrigen = service.revisarSaldo(1l);
//		saldoDestino = service.revisarSaldo(2L);
//		int total = service.revisarTotalTransferencias(1L);
//
//		assertEquals("900", saldoOrigen.toPlainString());
//		assertEquals("2100", saldoDestino.toPlainString());
//		assertEquals(1, total);
//
//		verify(cuentaRepository, times(3)).findById(1L);
//		verify(cuentaRepository, times(3)).findById(2L);
//		verify(cuentaRepository, times(2)).update(any(Cuenta.class));
//
//		verify(bancoRepository, times(2)).findById(1L);
//		verify(bancoRepository).update(any(Banco.class));
//		verify(cuentaRepository, times(6)).findById(anyLong());
//
//		verify(cuentaRepository, never()).findAll();
//		verify(bancoRepository, never()).findAll();
//
//	}
//
//	@Test
//	void contextLoadsExceptions() {
//
//		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
//		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
//
//		BigDecimal saldoOrigen = service.revisarSaldo(1l);
//		BigDecimal saldoDestino = service.revisarSaldo(2L);
//
//		assertEquals("1000", saldoOrigen.toPlainString());
//		assertEquals("2000", saldoDestino.toPlainString());
//
//		assertThrows(DineroInsuficienteException.class, () -> {
//			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
//		});
//
//		saldoOrigen = service.revisarSaldo(1l);
//		saldoDestino = service.revisarSaldo(2L);
//		int total = service.revisarTotalTransferencias(1L);
//
//		assertEquals("1000", saldoOrigen.toPlainString());
//		assertEquals("2000", saldoDestino.toPlainString());
//		assertEquals(0, total);
//
//		verify(cuentaRepository, times(3)).findById(1L);
//		verify(cuentaRepository, times(2)).findById(2L);
//		verify(cuentaRepository, never()).update(any(Cuenta.class));
//
//		verify(bancoRepository, times(1)).findById(1L);
//		verify(bancoRepository, never()).update(any(Banco.class));
//		verify(cuentaRepository, times(5)).findById(anyLong());
//		verify(cuentaRepository, never()).findAll();
//		verify(bancoRepository, never()).findAll();
//
//	}
//
//	@Test
//	void contextLoads3() {
//
//		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//
//		Cuenta cuenta1 = service.findById(1L);
//		Cuenta cuenta2 = service.findById(1L);
//
//		assertSame(cuenta1, cuenta2);
//		assertTrue(cuenta1 == cuenta2);
//		assertEquals("Víctor", service.findById(1L).getPersona());
//		assertEquals("Víctor", cuenta1.getPersona());
//		assertEquals("Víctor", cuenta2.getPersona());
//		verify(cuentaRepository, times(3)).findById(1L);
//
//	}
//
//}
