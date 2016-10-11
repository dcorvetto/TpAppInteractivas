package test;
/*Si les falla agregar en Project, Properties, Java Build Path la "Library" JUNIT 4*/
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

import controlador.Sistema;

public class SistemaIT {
	
	private Sistema sistema;
	
	protected void setUp() throws Exception {
		System.out.println("pasa setUp");
		sistema = Sistema.getInstancia();
		sistema.login("etesla", "admin");
	}

	@Test
	public void testCrearReclamoCantidades() {
		int codigoCliente = 1;
		Map<Integer,Integer> mapCodigoCantidad = new HashMap<Integer,Integer>();
		mapCodigoCantidad.put(1, 3);
		mapCodigoCantidad.put(2,4);
		String descripcion = "prueba reclamo Cantidad";
		
		sistema.crearReclamoCantidades(codigoCliente, mapCodigoCantidad, descripcion);
		
		assertTrue(true);
		
	}
	
	@Test
	public void testCrearReclamoZona() {
		int codigoCliente = 2;
		
		String descripcion = "prueba reclamo zona papa";
		
		sistema.crearReclamoZona(codigoCliente, "PALERMO", descripcion);
		
		assertTrue(true);
				
		
	}
	
	@Test
	public void testCrearReclamoFacturacion() {
		int codigoCliente = 3;

		String descripcion = "prueba reclamo facturacion yeah";
		Map<Integer,Date> mapIdFecha = new HashMap<Integer,Date>();
		mapIdFecha.put(1, new Date());
		
		sistema.crearReclamoFactura(codigoCliente, descripcion, mapIdFecha);
		
		assertTrue(true);
				
		
	}

	@Test
	public void testCrearReclamoCompuesto() {
		int codigoCliente = 2;

		String descripcion = "prueba reclamo compuesto";
		List<Integer> listaReclamosIds = new ArrayList<Integer>();
		listaReclamosIds.add(4);
		listaReclamosIds.add(5);


		sistema.crearReclamoCompuesto(codigoCliente, listaReclamosIds);

		assertTrue(true);


	}
}
