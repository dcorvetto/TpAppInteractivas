package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import persistencia.AdmPersistenciaFactura;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;

/**
 * Clase especializada en ayudar al Sistema en la creacion de los reclamos.
 * @author yairlisa
 *
 */
public class ReclamosHelper {
	
	
	public static List<EventoReclamo> crearEventoReclamoInicio() {
		
		List<EventoReclamo> listaEventos = new ArrayList<EventoReclamo>();
		EventoReclamo eventoReclamo = new EventoReclamo();
		eventoReclamo.setDetalle("");
		eventoReclamo.setEstado(EnumEstado.INGRESADO);
		eventoReclamo.setFecha(new Date());
		listaEventos.add(eventoReclamo);
		
		return listaEventos;
	}
	
	public static List<ItemFacturaReclamo> convertirMapaEnListaItemFacturaReclamos(Map<Integer, Date> mapIdFecha) {
		
		List<ItemFacturaReclamo> listaItemsFacturaReclamos = new ArrayList<ItemFacturaReclamo>();
		
		for (Map.Entry<Integer, Date> item : mapIdFecha.entrySet())
		{
			ItemFacturaReclamo itemFacturaReclamo = new ItemFacturaReclamo();
			itemFacturaReclamo.setFactura(AdmPersistenciaFactura.getInstancia().buscarPorId(item.getKey()));
			itemFacturaReclamo.setFechaFactura(item.getValue().getTime());
			listaItemsFacturaReclamos.add(itemFacturaReclamo);
		}

		return listaItemsFacturaReclamos;
	}
	
	
	public static List<ItemProductoReclamo> convertirMapaEnListaItemProductoReclamo(Map<Integer,Integer> mapCodigoCantidad) {
		
		List<ItemProductoReclamo> listaItemsProductoReclamos = new ArrayList<ItemProductoReclamo>();
		
		for (Map.Entry<Integer, Integer> item : mapCodigoCantidad.entrySet())
		{
			ItemProductoReclamo itemProductoReclamo = new ItemProductoReclamo();
			itemProductoReclamo.setProducto(Producto.buscarPorId(item.getKey()));
			itemProductoReclamo.setCantidad(item.getValue());
			listaItemsProductoReclamos.add(itemProductoReclamo);
		}

		return listaItemsProductoReclamos;
	}
}
