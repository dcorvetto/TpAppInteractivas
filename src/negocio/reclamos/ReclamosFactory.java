package negocio.reclamos;

import negocio.Cliente;
import negocio.Usuario;

/**
 * Clase encargada de la creacion de Reclamos.
 * @author yairlisa
 *
 */

public class ReclamosFactory {
	
	public static ReclamoZona crearReclamoZona(Cliente cliente, String descripcion, 
											   Usuario user, Usuario operador, 
											   Usuario responsable,String zona) {
		
		ReclamoZona reclamoPorZona = new ReclamoZona();
		reclamoPorZona.setCliente(cliente);
		reclamoPorZona.setDescripcion(descripcion);
		reclamoPorZona.setOperador(operador);
		reclamoPorZona.setResponsable(responsable);
		reclamoPorZona.setZona(zona);
		reclamoPorZona.setTiempoRespuesta(-1f);
		reclamoPorZona.setTipoReclamo(TipoReclamo.ZONA);
		
		return reclamoPorZona;
	}
	
	public static ReclamoFacturacion crearReclamoFacturacion(Cliente cliente, String descripcion,
													 Usuario user, Usuario operador, 
													 Usuario responsable) {
		
		ReclamoFacturacion reclamoFacturacion = new ReclamoFacturacion();
		reclamoFacturacion.setCliente(cliente);
		reclamoFacturacion.setDescripcion(descripcion);
		reclamoFacturacion.setOperador(operador);
		reclamoFacturacion.setResponsable(responsable);
		reclamoFacturacion.setTiempoRespuesta(-1f);
		reclamoFacturacion.setTipoReclamo(TipoReclamo.FACTURACION);
		
		return reclamoFacturacion;
		
	}
	
	public static ReclamoCantidad crearReclamoCantidad(Cliente cliente, String descripcion,
													   Usuario user, Usuario operador, 
													   Usuario responsable) {
		
		ReclamoCantidad reclamoCantidad = new ReclamoCantidad();
		reclamoCantidad.setCliente(cliente);
		reclamoCantidad.setDescripcion(descripcion);
		reclamoCantidad.setOperador(operador);
		reclamoCantidad.setResponsable(responsable);
		reclamoCantidad.setTiempoRespuesta(-1f);
		reclamoCantidad.setTipoReclamo(TipoReclamo.CANTIDAD);

		return reclamoCantidad;
	
	}
	
	
}
