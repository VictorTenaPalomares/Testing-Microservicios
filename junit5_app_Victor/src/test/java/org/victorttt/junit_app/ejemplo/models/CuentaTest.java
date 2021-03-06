package org.victorttt.junit_app.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.victorttt.junit_app.ejemplo.exceptions.DineroInsuficienteException;

class CuentaTest {
	Cuenta cuenta; // atributo que será independiente para cada método

	@BeforeEach
	void InitMetodoTest() {

		this.cuenta = new Cuenta("Víctor", new BigDecimal("1000.1234565"));
		System.out.println("Iniciando el método");
	}

	@AfterEach
	void finishMethod() {
		System.out.println("Finalizando el método");
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("Inicializando el test, antes de todo");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("Finalizando el test, después de todo");
	}

	@Nested
	class CuentaTestNombreSaldo {
		// 1. Crear clase y escribir método de prueba con anotacion @Test
		@Test
		@DisplayName(value = "Probando nombre de la cuenta")
		void testNombreCuenta() {

			// 2. Instanciar la clase que queremos probar

			// 3. Pasamos datos para probar (input), en este caso el seter este
			// cuenta.setPersona("Víctor");

			// 4. Definimos la espectativa
			String esperado = "Víctor";

			// 5. Reflejamos la realidad
			String real = cuenta.getPersona();
			assertNotNull(real, () -> "la cuenta no puede ser nula"); // MUY IMPORTANTE

			// 6. Para comprobar algo usamos la clase Assertions y alguno de sus métodos
			assertEquals(esperado, real, () -> "el nombre de la cuenta no es el que se esperaba");
			assertTrue(real.equals("Víctor"), () -> "nombre cuenta esperada debe ser igual a la real");
			// Con que falle solamente 1 método los tests no pasan
		}

		@Test
		@DisplayName(value = "Comprueba que haya saldo en la cuenta")
		void testSaldoCuenta() {
			assertNotNull(cuenta.getSaldo()); // MUY IMPORTANTE
			assertEquals(1000.1234565, cuenta.getSaldo().doubleValue());
			// Para comprobar que el saldo es positivo
			assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
			// Validamos lo mismo con la lógica inversa
			assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
		}

		// Desarrollando mediante patrón TDD
		@Test
		@DisplayName(value = "Testeando instancias que sean iguales con el método equals")
		void testReferenciaCuenta() {
			cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
			Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

			// Si no son iguales que pase la prueba
			// aquí aunque los objetos tienen el mismo contenido, al ser objetos se
			// comprueban por referencia
			// apuntan a direcciones de memoria distintas, por lo tanto son diferentes
			// assertNotEquals(cuenta2, cuenta);// Afirma que no son iguales

			// Ahora quitamos el not y el test falla, por lo tanto tenmemos que
			// arrglarlo, nos hemos dado cuenta antes de que se escriba el codigo en la
			// clase cuenta
			// y esta es la eseencia TDD, es decir, escribir las pruebas para que
			// fallen y a cntinuación escribir el código para que las pasen,
			// por último refactorizar para optimizar / limpiar nuestro código
			assertEquals(cuenta2, cuenta);// Afirma que no son iguales

			/*
			 * Después de sobrrescribir en la clase cuenta el método equals hemos cambiado
			 * la forma en la que se comprueba la igualdad entre objetos y la prueba la
			 * pasa. Por lo tanto, hemos desarrolladaprimero con la prueba y despues de que
			 * falle lo arreglamos y así se queda
			 */

		}

	}

	@Nested
	class CuentaOperacionesTest {
		@Test
		void testDebitoCuenta() {
			// Creamos un objeto para esta prueba
			cuenta = new Cuenta("Víctor", new BigDecimal("1000.1234565"));

			// Le aplicamos el método débito que aún NO ESTÁ IMPLEMENTADO
			cuenta.debito(new BigDecimal(100));

			// Validamos que el saldo de la cuenta no sea nulo
			assertNotNull(cuenta.getSaldo()); // MUY IMPORTANTE

			// Por lo tanto el resultado esperado es= 900.1234565
			assertEquals(900, cuenta.getSaldo().intValue()); // afirma que la espectativa y la realidad sean iguales
			assertEquals("900.1234565", cuenta.getSaldo().toPlainString());

		}

		@Test
		void creditoDebitoCuenta() {
			cuenta = new Cuenta("Víctor", new BigDecimal("1000.1234565"));

			cuenta.credito(new BigDecimal(100));

			assertNotNull(cuenta.getSaldo());

			assertEquals(1100, cuenta.getSaldo().intValue());
			assertEquals("1100.1234565", cuenta.getSaldo().toPlainString());

		}

		@Test
		void testDineroInsuficienteExceptionCuenta() {
			// Otra vez instanciamos la clase cuenta unicamente con el objeto de comprobar
			// este método
			cuenta = new Cuenta("Víctor", new BigDecimal("1000.1234565"));

			// Afirma lanzamiento, recibe clase de excepción y la ejecucion que la
			// desencadena
			// en este caso es una expresión lambda dentro del propio paso de parámetro
			Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
				cuenta.debito(new BigDecimal(1500)); // pasamos un valor mayor al que ahora hay
			});

			String real = exception.getMessage();
			String esperado = "Dinero Insuficiente";
			assertEquals(esperado, real);
		}
	}

	// 1. Crear clase y escribir método de prueba con anotacion @Test
	@Test
	@DisplayName(value = "Probando nombre de la cuenta")
	void testNombreCuenta() {

		// 2. Instanciar la clase que queremos probar

		// 3. Pasamos datos para probar (input), en este caso el seter este
		// cuenta.setPersona("Víctor");

		// 4. Definimos la espectativa
		String esperado = "Víctor";

		// 5. Reflejamos la realidad
		String real = cuenta.getPersona();
		assertNotNull(real, () -> "la cuenta no puede ser nula"); // MUY IMPORTANTE

		// 6. Para comprobar algo usamos la clase Assertions y alguno de sus métodos
		assertEquals(esperado, real, () -> "el nombre de la cuenta no es el que se esperaba");
		assertTrue(real.equals("Víctor"), () -> "nombre cuenta esperada debe ser igual a la real");
		// Con que falle solamente 1 método los tests no pasan
	}

	@Test
	@DisplayName(value = "Comprueba que haya saldo en la cuenta")
	void testSaldoCuenta() {
		assertNotNull(cuenta.getSaldo()); // MUY IMPORTANTE
		assertEquals(1000.1234565, cuenta.getSaldo().doubleValue());
		// Para comprobar que el saldo es positivo
		assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
		// Validamos lo mismo con la lógica inversa
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}

	// Desarrollando mediante patrón TDD
	@Test
	@DisplayName(value = "Testeando instancias que sean iguales con el método equals")
	void testReferenciaCuenta() {
		cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
		Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

		// Si no son iguales que pase la prueba
		// aquí aunque los objetos tienen el mismo contenido, al ser objetos se
		// comprueban por referencia
		// apuntan a direcciones de memoria distintas, por lo tanto son diferentes
		// assertNotEquals(cuenta2, cuenta);// Afirma que no son iguales

		// Ahora quitamos el not y el test falla, por lo tanto tenmemos que
		// arrglarlo, nos hemos dado cuenta antes de que se escriba el codigo en la
		// clase cuenta
		// y esta es la eseencia TDD, es decir, escribir las pruebas para que
		// fallen y a cntinuación escribir el código para que las pasen,
		// por último refactorizar para optimizar / limpiar nuestro código
		assertEquals(cuenta2, cuenta);// Afirma que no son iguales

		/*
		 * Después de sobrrescribir en la clase cuenta el método equals hemos cambiado
		 * la forma en la que se comprueba la igualdad entre objetos y la prueba la
		 * pasa. Por lo tanto, hemos desarrolladaprimero con la prueba y despues de que
		 * falle lo arreglamos y así se queda
		 */

	}

	@Test
	void transferirDineroCuentas() {

		// Creamos el caso de uso para este test con objetos que se generan con las
		// cuentas
		Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Víctor", new BigDecimal("1500.8989"));
		Banco banco = new Banco();
		banco.setNombre("Banco del estado");
		banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

		// Lo que se escribe abajo debe ser verdad para que la prueba se cumpla
		assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
		assertEquals("3000", cuenta1.getSaldo().toPlainString());

	}

	@Test // para que se ejecute este trozo de código como test
	@DisplayName(value = "Probando relaciones de las cuentas y el banco con assert all")
	// @Disabled // este método no se ejecutaría
	void testRelacionBancoCuentas() {
		// fail(); Así forzamos el fallo
		// Creamos el caso de uso para este test con objetos que se generan con las
		// cuentas
		Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Víctor", new BigDecimal("1500.8989"));
		Banco banco = new Banco();
		banco.addCuenta(cuenta1);
		banco.addCuenta(cuenta2);

		banco.setNombre("Banco del estado");
		banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

		// Nidamos todas las afirmaciones dentro de una sola
		assertAll(
				() -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
						() -> "el saldo del cuenta es incorrecto"),
				() -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
						() -> "el saldo del cuenta es incorrecto"),
				() -> assertEquals(2, banco.getCuentas().size(), () -> "el banco no tiene las cuentas esperadas"),
				() -> assertEquals("Banco del estado", cuenta1.getBanco().getNombre(),
						() -> "el nombre del banco no es el esperado"),
				() -> assertEquals("Banco del estado", cuenta1.getBanco().getNombre(),
						() -> "el nombre del banco no es el esperado"),
				() -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Víctor")))

		);

		/*
		 * // Probamos que el banco que se ha creado tenga el nombre correcto //
		 * invocándolo desde la clase cuenta, falla. ¿por que? por que no está //
		 * establecida la relación inversa, la prueba nos ha servido para darnos cuenta
		 * assertEquals("Banco del estado.", cuenta1.getBanco().getNombre());
		 * 
		 * // Comprobar que una cuenta pertenezca a una persona a través de streams
		 * assertEquals("Víctor", banco.getCuentas().stream().filter(c ->
		 * c.getPersona().equals("Víctor")).findFirst() .get().getPersona());
		 * 
		 * // Otra forma , más corta assertTrue(banco.getCuentas().stream().filter(c ->
		 * c.getPersona().equals("Víctor")).findFirst().isPresent());
		 * 
		 * // Otra forma, aún más corta
		 * assertTrue(banco.getCuentas().stream().anyMatch(c ->
		 * c.getPersona().equals("Víctor")));
		 */
	}

	@Nested
	class SistemaOerativoTest {
		@Test
		@EnabledOnOs(OS.WINDOWS)
		void soloWindows() {

		}

		@Test
		@EnabledOnOs({ OS.LINUX, OS.MAC })
		void soloLinuxMac() {
			// No va a pasar este test porque estamos en windows
		}

		@Test
		@DisabledOnOs(OS.WINDOWS)
		void desactivadoWindows() {
			// No va a pasra porque estamos en windows
		}
	}

	@Nested
	class JaveVersionTest {
		@Test
		@EnabledOnJre(JRE.JAVA_16)
		void soloEnJava16() {
			// se va a pasar unicamente si se ejecuta en un JVM que sea como la del
			// parámetro
		}
	}

	@Nested
	class SystemPropertiesTest {
		// Para ver las propiedades del sistema donde corre el test
		@Test
		@Disabled
		void imprimirSystemProperties() {
			Properties properties = System.getProperties();
			properties.forEach((k, v) -> {
				System.out.println(k + ":" + v);
			});
		}

		// Para ejecutar en caso de que se cumpla una propiedad de sistema determinada
		@Test
		@EnabledIfSystemProperty(named = "java.version", matches = "16.0.1")
		void pasarSiVersionJava() {

			System.out.println("Se imprime solo si he acertado la versión de java");
		}
	}

}
