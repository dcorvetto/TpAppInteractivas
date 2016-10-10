package test;

import persistencia.AdmPersistenciaCliente;
import persistencia.AdmPersistenciaProducto;
import persistencia.AdmPersistenciaReclamo;
import persistencia.AdmPersistenciaUsuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import negocio.Cliente;
import negocio.EnumEstado;
import negocio.EventoReclamo;
import negocio.Producto;
import negocio.Reclamo;
import negocio.Usuario;
import negocio.reclamos.ItemProductoReclamoFaltantes;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoFaltantes;
import negocio.reclamos.TipoReclamo;

public class Test {

	public static void main(String[] args) {
		ReclamoFaltantes r = new ReclamoFaltantes();
		Cliente c = AdmPersistenciaCliente.getInstancia().buscarCliente(1);
		Usuario operador = AdmPersistenciaUsuario.getInstancia().buscarUsuario(1);
		Usuario resp = AdmPersistenciaUsuario.getInstancia().buscarUsuario(2);

		r.setCliente(c);
		r.setDescripcion("Reclamo ejemplo faltante");
		r.setEstaSolucionado(false);
		r.setOperador(operador);
		r.setResponsable(resp);
		r.setTiempoRespuesta(-1f);
		r.setTipoReclamo(TipoReclamo.FALTANTES);
		r.setZona(null);
		
		/*Insertar item faltantes asociado al reclamo anterior*/
		ItemProductoReclamoFaltantes iprf = new ItemProductoReclamoFaltantes();
		iprf.setCantidadFacturada(5);
		iprf.setCantidadFaltante(2);
		Producto producto = AdmPersistenciaProducto.getInstancia().buscarProducto(1);
		iprf.setProducto(producto);

		List<ItemProductoReclamoFaltantes> lista = new ArrayList<ItemProductoReclamoFaltantes>();
		lista.add(iprf);
		r.setItems(lista);
		
		EventoReclamo er = new EventoReclamo();
		er.setDetalle("DETALLE EVENTO 1");
		er.setEstado(EnumEstado.INGRESADO);
		er.setFecha(new Date());
	
		List<EventoReclamo> listaEventos = new ArrayList<EventoReclamo>();
		listaEventos.add(er);
		r.setEventos(listaEventos);
		
		AdmPersistenciaReclamo.getInstancia().insert(r);

		/*Ejemplo reclamo compuesto*/		
		ReclamoCompuesto rc = new ReclamoCompuesto();
		Cliente cc = AdmPersistenciaCliente.getInstancia().buscarCliente(1);
		Usuario operadorc = AdmPersistenciaUsuario.getInstancia().buscarUsuario(1);
		Usuario respc = AdmPersistenciaUsuario.getInstancia().buscarUsuario(2);

		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(AdmPersistenciaReclamo.getInstancia().buscarReclamo(1));
		
		
		rc.setCliente(cc);
		rc.setDescripcion("Reclamo compuesto");
		rc.setEstaSolucionado(false);
		rc.setOperador(operadorc);
		rc.setResponsable(respc);
		rc.setTiempoRespuesta(-1f);
		rc.setTipoReclamo(TipoReclamo.COMPUESTO);
		rc.setZona(null);
		rc.setReclamos(listaReclamos);
		
		AdmPersistenciaReclamo.getInstancia().insert(rc);

}
}
