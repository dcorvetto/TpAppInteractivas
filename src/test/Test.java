package test;

import persistencia.AdmPersistenciaCliente;
import persistencia.AdmPersistenciaEventoReclamo;
import persistencia.AdmPersistenciaProducto;
import persistencia.AdmPersistenciaReclamo;
import persistencia.AdmPersistenciaUsuario;

import java.util.Date;

import negocio.Cliente;
import negocio.EnumEstado;
import negocio.EventoReclamo;
import negocio.Producto;
import negocio.Reclamo;
import negocio.Usuario;
import negocio.reclamos.ItemProductoReclamoFaltantes;

public class Test {

	public static void main(String[] args) {
		Reclamo r = new Reclamo();
		Cliente c = AdmPersistenciaCliente.getInstancia().buscarCliente(1);
		Usuario operador = AdmPersistenciaUsuario.getInstancia().buscarUsuario(1);
		Usuario resp = AdmPersistenciaUsuario.getInstancia().buscarUsuario(2);

		r.setCliente(c);
		r.setDescripcion("Reclamo ejemplo faltante");
		r.setEstaSolucionado(false);
		r.setOperador(operador);
		r.setResponsable(resp);
		r.setTiempoRespuesta(-1f);
		r.setTipoReclamo("faltante");
		r.setZona(null);
		
		int idReclamo = AdmPersistenciaReclamo.getInstancia().insertar(r);
		
		/*Insertar item faltantes asociado al reclamo anterior*/
		ItemProductoReclamoFaltantes iprf = new ItemProductoReclamoFaltantes();
		iprf.setCantidadFacturada(5);
		iprf.setCantidadFaltante(2);
		Producto producto = AdmPersistenciaProducto.getInstancia().buscarProducto(1);
		iprf.setProducto(producto);
		
		AdmPersistenciaReclamo.getInstancia().insertarItems(idReclamo, iprf);
		
		/*Crear reclamo Compuesto y relacionar con el reclamo anterior*/
		
		Reclamo rc = new Reclamo();
		Cliente cc = AdmPersistenciaCliente.getInstancia().buscarCliente(1);
		Usuario operadorc = AdmPersistenciaUsuario.getInstancia().buscarUsuario(1);
		Usuario respc = AdmPersistenciaUsuario.getInstancia().buscarUsuario(2);

		rc.setCliente(cc);
		rc.setDescripcion("Reclamo compuesto");
		rc.setEstaSolucionado(false);
		rc.setOperador(operadorc);
		rc.setResponsable(respc);
		rc.setTiempoRespuesta(-1f);
		rc.setTipoReclamo("compuesto");
		rc.setZona(null);
		
		int idReclamoc = AdmPersistenciaReclamo.getInstancia().insertar(rc);
		Reclamo rhijo = AdmPersistenciaReclamo.getInstancia().buscarReclamo(idReclamo);
		
		AdmPersistenciaReclamo.getInstancia().insertarReclamoCompuesto(idReclamoc, rhijo);
		

		/*Borrar el reclamo insertado*/
	//	AdmPersistenciaReclamo.getInstancia().delete(rhijo);
	//	AdmPersistenciaReclamo.getInstancia().delete(AdmPersistenciaReclamo.getInstancia().buscarReclamo(idReclamoc));
	
		EventoReclamo er = new EventoReclamo();
		er.setDetalle("DETALLE EVENTO 1");
		er.setEstado(EnumEstado.INGRESADO);
		er.setFecha(new Date());
		er.setIdReclamo(AdmPersistenciaReclamo.getInstancia().buscarReclamo(idReclamo).getNumero());
		
		AdmPersistenciaEventoReclamo.getInstancia().insert(er);
	
	}

}
