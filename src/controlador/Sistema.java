package controlador;

import negocio.*;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ReclamoProducto;
import negocio.views.ClienteView;
import negocio.views.EventoReclamoView;
import negocio.views.ReclamoTPromXOperadorView;
import negocio.views.ReclamoView;
import persistencia.AdmPersistenciaCliente;
import persistencia.AdmPersistenciaProducto;
import persistencia.AdmPersistenciaReclamo;
import persistencia.AdmPersistenciaUsuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Sistema {

	private Collection<Cliente> clientes;
	private Collection<Reclamo> reclamos;
	private Collection<Usuario> usuarios;

	private static Sistema instancia = null;

	public Sistema() {
	}

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	public Collection<ClienteView> getClientesMayorCantReclamos() {
		return null;
	}

	public Collection<ClienteView> getClientes() {
		Collection<ClienteView> clientesView =  new ArrayList<>();
		for (Cliente cliente : AdmPersistenciaCliente.getInstancia().selectAll()) {
			ClienteView cV = new ClienteView(String.valueOf(cliente.getCodigo_cliente()), cliente.getNombre(), String.valueOf(cliente.getCantReclamos()), cliente.getMail());
			clientesView.add(cV);
		}
		return clientesView;
	}

	public Collection<String> getCodigoProductos() {
		Collection<String> productos = new ArrayList<>();
		for (Producto producto : AdmPersistenciaProducto.getInstancia().selectAll()) {
			productos.add(String.valueOf(producto.getCodigo()));
		}
		return productos;
	}

	public int getCantidadReclamosMes(int mes) {
		return 0;
	}

	public Collection<ReclamoTPromXOperadorView> getTiempoPromedio(int identificadorUsuario) {
		return null;
	}

	public Collection<EventoReclamoView> getTratamientoReclamo(int numeroReclamo) {
		Reclamo reclamo = buscarReclamo(numeroReclamo);
		Collection<EventoReclamoView> eventosReclamoView = new ArrayList<>();
		if (reclamo != null) {
			for (EventoReclamo eventoReclamo : reclamo.getEventos()) {
				EventoReclamoView evento = new EventoReclamoView(eventoReclamo.getEstado().getTexto(), eventoReclamo.getFecha(), eventoReclamo.getDetalle());
				eventosReclamoView.add(evento);
			}
		}
		return eventosReclamoView;
	}

	/**
	 * Devuelve solo los reclamos que puede ver cada usuario (dependiendo de sus roles)
	 * Ejemplo: los que posean de rol zona_entrega solo podran ver los reclamos de tipo zona
	 * */
	public Collection<ReclamoView> getReclamosParaUsuario(int numUsuario){
		Collection<String> tiposDeReclamos = new ArrayList<>();
		Collection<EnumRoles> roles = Sistema.getInstancia().rolesUsuario(numUsuario);
		if(roles.contains(EnumRoles.ADMINISTRACION) || roles.contains(EnumRoles.CALL_CENTER) || roles.contains(EnumRoles.CONSULTA)){
			tiposDeReclamos.add("producto");
			tiposDeReclamos.add("faltante");
			tiposDeReclamos.add("cant");
			tiposDeReclamos.add("facturacion");
			tiposDeReclamos.add("zona");
		}
		if(roles.contains(EnumRoles.DISTRIBUCION)){
			tiposDeReclamos.add("producto");
			tiposDeReclamos.add("faltante");
			tiposDeReclamos.add("cant");
		}
		if(roles.contains(EnumRoles.ZONA_ENTREGA)){
			tiposDeReclamos.add("zona");
		}
		if(roles.contains(EnumRoles.FACTURACION)){
			tiposDeReclamos.add("facturacion");
		}

		Collection<ReclamoView> reclamosView = new ArrayList<>();
		for (Reclamo reclamo : AdmPersistenciaReclamo.getInstancia().buscarReclamos()) {
			if(tiposDeReclamos.contains(reclamo.getTipoReclamo())){
				ReclamoView reclamoV = new ReclamoView(reclamo.getNumero(), reclamo.getDescripcion(),
						reclamo.getTipoReclamo(), reclamo.isEstaSolucionado());
				reclamosView.add(reclamoV);
			}
		}
		return reclamosView;
	}
	
//	public void iniciarReclamo(String desc, int codigo_cliente, int operador, int cod_responsable, boolean es_compuesto){
//		Reclamo reclamoNuevo = new Reclamo(reclamos.size()+1, desc);
//		reclamoNuevo.setOperador(buscarUsuario(operador));
//		reclamoNuevo.setCliente(buscarCliente(codigo_cliente));
//		reclamoNuevo.setResponsable(buscarUsuario(cod_responsable));
//		reclamoNuevo.guardarCambios();
//		reclamos.add(reclamoNuevo);
//	}

	public void crearReclamoProducto(int codigo_cliente, HashMap<Integer, Integer> mapCodigoCantidad) { 
		/*Agrego ejemplo por si sirve*/
		Cliente c = AdmPersistenciaCliente.getInstancia().buscarCliente(codigo_cliente);
		ReclamoProducto r = new ReclamoProducto();
		r.setCliente(c);
		//r.setDescripcion(descripcion);
		r.setEstaSolucionado(false);
		//r.setOperador(operador);
		//r.setResponsable(responsable);
		r.setTiempoRespuesta(-1f);
		r.setTipoReclamo("producto");
		
		List<ItemProductoReclamo> listaItems = new ArrayList<ItemProductoReclamo>();
		
		for (Map.Entry<Integer, Integer> item : mapCodigoCantidad.entrySet()) {
			ItemProductoReclamo ipr = new ItemProductoReclamo();
			ipr.setProducto(AdmPersistenciaProducto.getInstancia().buscarProducto(item.getKey()));
			ipr.setCantidad(item.getValue());
			listaItems.add(ipr);
		}
		
		r.setItems(listaItems);
		
		List<EventoReclamo> listaEventos = new ArrayList<EventoReclamo>();
		EventoReclamo er = new EventoReclamo();
		er.setDetalle("");
		er.setEstado(EnumEstado.INGRESADO);
		er.setFecha(new Date());
		listaEventos.add(er);
		
		r.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(r);
		this.setReclamos(listaReclamos);
	
		r.guardarCambios();
	}

	public void crearReclamoCantidades(int codigo_cliente, Map<Integer, Integer> mapCodigoCantidad) { // Map<codigo_producto,cantidad>
	}

	public void crearReclamoZona(int codigo_cliente, String zona) {
	}

	public void crearReclamoFactura(int codigo_cliente, Date fecha, int cod_factura) {
	}

	public void crearReclamoFaltantes(int codigo_cliente, int cod_producto, int cant_socilitada, int cant_recibidad) {
	}

	private void agregarItemReclamoProd(int codigo_prod, int cant) {
	}

	private void agregarItemReclamoProdFaltante(int codigo_prod, int cant_facturada, int cant_entregada) {
	}

	private void agregarItemReclamoZona(String zona) {
	}

	private void agregarItemReclamoFactura(Date fecha, int identificador_factura) {
	}

	public void actualizarReclamo(Date fecha, String estado, int codigo_reclamo, String descripcion) {
		//EnumEstado.valueOf(estado.replace(" ", "_").toUpperCase()
	}

	/**
	 * Consulta en la base de usuarios si existe el usuario y el password pasados como parametros
	 * @return Devuelve el codigo de usuario si el usuario y password son correctos, y null en caso contrario
	 */
	public Integer login(String usuario, String password) {
		Usuario user = AdmPersistenciaUsuario.getInstancia().buscarUsuario(usuario, password);
		if(user==null){
			return null;
		}else{
			return user.getCodigo();
		}
	}
	
	/**
	 * @return Devuelve los roles que tiene el usuario con el codigo pasado como parametro
	 */
	public Collection<EnumRoles> rolesUsuario(int numUsuario) {
		return buscarUsuario(numUsuario).getRoles();
	}

	private Reclamo buscarReclamo(int numeroReclamo) {
		return null;
	}

	private Usuario buscarUsuario(int numUsuario) {
		Usuario usuario = AdmPersistenciaUsuario.getInstancia().buscarUsuario(numUsuario);
		return usuario;
	}

	private Cliente buscarCliente(int numCliente) {
		return null;
	}

	private Producto buscarProducto(int cod_producto) {
		return null;
	}

	public Collection<Reclamo> getReclamos() {
		return reclamos;
	}

	public void setReclamos(Collection<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void setClientes(Collection<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	

}