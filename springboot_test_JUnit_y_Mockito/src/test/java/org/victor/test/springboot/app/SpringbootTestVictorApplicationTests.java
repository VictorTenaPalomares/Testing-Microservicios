//package org.victor.test.springboot.app;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.victor.test.springboot.app.exceptions.DineroInsuficienteException;
//import org.victor.test.springboot.app.models.Banco;
//import org.victor.test.springboot.app.models.Cuenta;
//import org.victor.test.springboot.app.repositories.BancoRepository;
//import org.victor.test.springboot.app.repositories.CuentaRepository;
//import org.victor.test.springboot.app.services.CuentaService;
//import org.victor.test.springboot.app.services.CuentaServiceImpl;
//
//@SpringBootTest
//class SpringbootTestVictorApplicationTests {
//
//	// 1. Creamos nuestros atributos (Mocks)
//	CuentaRepository cuentaRepository;
//	BancoRepository bancoRepository;
//
//	// 2. Traemos la capa de servicio como atributo
//	CuentaService service;
//
//	// 3. Antes de la ejecucion de cada método instanciamos atributos
//	// pero siempre burlándolos
//	@BeforeEach
//	void setUp() {
//		cuentaRepository = mock(CuentaRepository.class);
//		bancoRepository = mock(BancoRepository.class);
//
//		// Implementación concreta de service
//		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
//
//		// Reinicio de datos antes de que se ejecute cada método para que no hay
//		// dependencia de datos entre ellos
//		Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
//		Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
//		Datos.BANCO.setTotalTransferencias(0);
//	}
//
//	// 4. Una vez lo hemos traido todo empezamos a probar
//	@Test
//	void contextLoads() {
//
//		// Dado que... aquí van siempre los mocks
//		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
//		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
//		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
//
//		// Cuando... aquí siempre lo quequeremos probar, el service
//		// La cuenta 1 transfiere dineros a la cuenta 2
//		BigDecimal saldoOrigen = service.revisarSaldo(1l);
//		BigDecimal saldoDestino = service.revisarSaldo(2L);
//
//		// Entonces...
//		// Probamos expectativa vs realidad
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
//		// Ahora verificamos cuantas veces se han lanzado los métodos de cada Mock
//		// (repositorio)
//		// verify sin times se asegura que solo se invoque una vez, por lo tanto...
//		verify(cuentaRepository, times(3)).findById(1L);
//		verify(cuentaRepository, times(3)).findById(2L);
//		verify(cuentaRepository, times(2)).update(any(Cuenta.class));
//
//		verify(bancoRepository, times(2)).findById(1L);
//		verify(bancoRepository).update(any(Banco.class));
//		verify(cuentaRepository, times(6)).findById(anyLong());
//
//		// Verificamos que el findAll nunca se están llamando
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
//		// Ahora verificamos que las excepciones se manejen adecuadamente
//		// para ello a partir de donde debe lanzar anidamos con assertThrow
//		// que recibe la excepción que debe lanzar y una implementacion de la
//		// interfaz funcional executable, desarrolamos con lambda
//		assertThrows(DineroInsuficienteException.class, () -> {
//			/************************************************/
//			// recordemos que en la cuenta origen solo hay 1000 pesetas
//			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
//		});
//
//		saldoOrigen = service.revisarSaldo(1l);
//		saldoDestino = service.revisarSaldo(2L);
//		int total = service.revisarTotalTransferencias(1L);
//
//		// Como se lanza la excepción, el dinero permanece inalterado
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
//		// Verificamos que el findAll nunca se están llamando
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
//		Cuenta cuenta1=service.findById(1L);
//		Cuenta cuenta2=service.findById(1L);
//		
//		assertSame(cuenta1,cuenta2);
//		assertTrue(cuenta1==cuenta2);
//		assertEquals("Víctor",service.findById(1L).getPersona());
//		assertEquals("Víctor",cuenta1.getPersona());
//		assertEquals("Víctor",cuenta2.getPersona());
//		verify(cuentaRepository,times(3)).findById(1L);
//		
//	}
//	
//
//}
