package controlador;

import negocio.*;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ReclamoCantidad;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoFacturacion;
import negocio.reclamos.ReclamoProducto;
import negocio.reclamos.ReclamoZona;
import negocio.reclamos.ReclamosFactory;
import negocio.reclamos.TipoReclamo;
import negocio.views.ClienteView;
import negocio.views.EventoReclamoView;
import negocio.views.ReclamoTPromXOperadorView;
import negocio.views.ReclamoView;


import java.util.*;

public class Sistema {

	private Collection<Cliente> clientes;
	private Collection<Reclamo> reclamos;
	private Collection<Usuario> usuarios;
	private Usuario user;

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
		for (Cliente cliente : Cliente.obtenerTodos()) {
			ClienteView cV = new ClienteView(String.valueOf(cliente.getCodigo_cliente()), cliente.getNombre(), String.valueOf(cliente.getCantReclamos()), cliente.getMail());
			clientesView.add(cV);
		}
		return clientesView;
	}

	public Collection<String> getCodigoProductos() {
		Collection<String> productos = new ArrayList<>();
		for (Producto producto : Producto.obtenerTodos()) {
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

	/**
	 * Solo pueden crear eventos aquelos que posean los roles: Administrador, Facturacion, Distribucion, o Zona de Entrega
	 * Los de Call Center y  Consulta no pueden
	 * @return true si puede crear eventos, y false en caso contrario
	 * */
	public boolean puedeCrearEventos(int codigoUsuario) {
		Usuario usuario = buscarUsuario(codigoUsuario);
		Collection<EnumRoles> roles = usuario.getRoles();
		boolean puede = false;
		if(roles.contains(EnumRoles.ADMINISTRACION) || roles.contains(EnumRoles.FACTURACION) || roles.contains(EnumRoles.DISTRIBUCION) || roles.contains(EnumRoles.ZONA_ENTREGA)){
			puede = true;
		}
		return puede;
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
			tiposDeReclamos.add(TipoReclamo.PRODUCTO.toString());
			tiposDeReclamos.add(TipoReclamo.FALTANTES.toString());
			tiposDeReclamos.add(TipoReclamo.CANTIDAD.toString());
			tiposDeReclamos.add(TipoReclamo.FACTURACION.toString());
			tiposDeReclamos.add(TipoReclamo.ZONA.toString());
			tiposDeReclamos.add(TipoReclamo.COMPUESTO.toString());
		}
		if(roles.contains(EnumRoles.DISTRIBUCION)){
			tiposDeReclamos.add(TipoReclamo.PRODUCTO.toString());
			tiposDeReclamos.add(TipoReclamo.FALTANTES.toString());
			tiposDeReclamos.add(TipoReclamo.CANTIDAD.toString());
		}
		if(roles.contains(EnumRoles.ZONA_ENTREGA)){
			tiposDeReclamos.add(TipoReclamo.ZONA.toString());
		}
		if(roles.contains(EnumRoles.FACTURACION)){
			tiposDeReclamos.add(TipoReclamo.FACTURACION.toString());
		}

		Collection<ReclamoView> reclamosView = new ArrayList<>();
		List<Reclamo> reclamos = (List<Reclamo>) Reclamo.obtenerTodos(); 
		for (Reclamo reclamo : reclamos ) {
			if(reclamo.getTipoReclamo()!=null){
				if(tiposDeReclamos.contains(reclamo.getTipoReclamo().toString())){
					ReclamoView reclamoV = new ReclamoView(reclamo.getNumero(), reclamo.getDescripcion(),
							reclamo.getTipoReclamo().getDescripcionTipo(), reclamo.isEstaSolucionado());
					reclamosView.add(reclamoV);
				}
			}
		}
		return reclamosView;
	}
	
	public void iniciarReclamo(String desc, int codigo_cliente, int operador, int cod_responsable, boolean es_compuesto){
	if (!es_compuesto){
		Reclamo reclamoNuevo = new Reclamo(reclamos.size()+1, desc);
		reclamoNuevo.setOperador(buscarUsuario(operador));
		reclamoNuevo.setCliente(buscarCliente(codigo_cliente));
		reclamoNuevo.setResponsable(buscarUsuario(cod_responsable));
		reclamoNuevo.guardarCambios();
		}
	}

	public void crearReclamoProducto(int codigo_cliente, HashMap<Integer, Integer> mapCodigoCantidad, String descripcion) {
	//	Agrego ejemplo por si sirve
		Cliente c = Cliente.buscarPorCodigo(codigo_cliente);
		ReclamoProducto r = new ReclamoProducto();
		r.setCliente(c);
		r.setDescripcion(descripcion);
		r.setEstaSolucionado(false);
		r.setOperador(user);
		//TODO: cambiar el parametro de abajo y pasarle un responsable que tendria que recibir por parametro
		r.setResponsable(user);
		r.setTiempoRespuesta(-1f);
		r.setTipoReclamo(TipoReclamo.PRODUCTO);

		List<ItemProductoReclamo> listaItems = new ArrayList<ItemProductoReclamo>();

		for (Map.Entry<Integer, Integer> item : mapCodigoCantidad.entrySet()) {
			ItemProductoReclamo ipr = new ItemProductoReclamo();
			ipr.setProducto(Producto.buscarProducto(item.getKey()));
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

	public void crearReclamoCantidades(int codigo_cliente, Map<Integer, Integer> mapCodigoCantidad, String descripcion) { 
		Cliente cliente = Cliente.buscarPorCodigo(codigo_cliente);
		//TODO:Cuando se agregue en UI el operador y responsable.
		//Usuario operador = Usuario.buscarPorId(cod_operador);
		//Usuario responsable = Usuario.buscarPorId(cod_responsable);
		
		ReclamoCantidad reclamoCantidad = ReclamosFactory.crearReclamoCantidad(cliente, descripcion, 
																				user, user, 
																				user);
		
		List<ItemProductoReclamo> listaItemProductoReclamo = ReclamosHelper.convertirMapaEnListaItemProductoReclamo(mapCodigoCantidad);
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoCantidad.setItems(listaItemProductoReclamo);
		reclamoCantidad.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoCantidad);
		this.setReclamos(listaReclamos);
		
		reclamoCantidad.guardarCambios();
		

	}

	public void crearReclamoZona(int codigo_cliente, String zona, String descripcion) {
		Cliente cliente = Cliente.buscarPorCodigo(codigo_cliente);
		//TODO:Cuando se agregue operador y responsable en la UI.
		//Usuario operador = Usuario.buscarPorId(cod_operador);
		//Usuario responsable = Usuario.buscarPorId(cod_responsable);
		
		ReclamoZona reclamoPorZona = ReclamosFactory.crearReclamoZona(cliente, descripcion, 
																	  user, user, 
																	  user, zona);
		
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoPorZona.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoPorZona);
		this.setReclamos(listaReclamos);

		reclamoPorZona.guardarCambios();
		
	}

	public void crearReclamoFactura(int codigo_cliente, String descripcion, Map<Integer, Date> mapIdFecha) { //Map<id_factura,fecha_factura>
		Cliente cliente = Cliente.buscarPorCodigo(codigo_cliente);
		//TODO:Cuando se agregue el operador y responsable en UI.
		//Usuario operador = Usuario.buscarPorId(cod_operador);
		//Usuario responsable = Usuario.buscarPorId(cod_responsable);
		
		ReclamoFacturacion reclamoFacturacion = ReclamosFactory.crearReclamoFacturacion(cliente, descripcion, 
																			 user, user, user);
		
		List<ItemFacturaReclamo> listaItemReclamoFacturas = ReclamosHelper.convertirMapaEnListaItemFacturaReclamos(mapIdFecha);
		List<EventoReclamo> listaEventos = ReclamosHelper.crearEventoReclamoInicio();
		
		reclamoFacturacion.setItems(listaItemReclamoFacturas);
		reclamoFacturacion.setEventos(listaEventos);
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		listaReclamos.add(reclamoFacturacion);
		this.setReclamos(listaReclamos);
		
		reclamoFacturacion.guardarCambios();
		
	}

	public void crearReclamoFaltantes(int codigo_cliente, int cod_producto, int cant_socilitada, int cant_recibidad, String descripcion) {
	}

	public void crearReclamoCompuesto(int codigo_cliente, List<Integer> ids_reclamos) {
		ReclamoCompuesto rc = new ReclamoCompuesto();
		Cliente cc = Cliente.buscarPorCodigo(codigo_cliente);
		Usuario operadorc = user;
		//TODO: cambiar linea de abajo para agregar responsable que vendra por parametro
		Usuario respc = user;
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		for(int i=0; i<ids_reclamos.size();i++){
			listaReclamos.add(Reclamo.buscarReclamo(ids_reclamos.get(i)));
		}	
		rc.setCliente(cc);
		rc.setDescripcion("Reclamo compuesto");
		rc.setEstaSolucionado(false);
		rc.setOperador(operadorc);
		rc.setResponsable(respc);
		rc.setTiempoRespuesta(-1f);
		rc.setTipoReclamo(TipoReclamo.COMPUESTO);
		rc.setZona(null);
		rc.setReclamos(listaReclamos);
		
		rc.guardarCambios();
	}

	private void agregarItemReclamoProd(int codigo_prod, int cant) {
	}

	private void agregarItemReclamoProdFaltante(int codigo_prod, int cant_facturada, int cant_entregada) {
	}

	private void agregarItemReclamoZona(String zona) {
	}

	private void agregarItemReclamoFactura(Date fecha, int identificador_factura, int codigo_cliente) {
	}

	public void actualizarReclamo(Date fecha, String estado, int codigo_reclamo, String detalle, Integer codigoUsuario) {
		//EnumEstado.valueOf(estado.replace(" ", "_").toUpperCase()
	}

	/**
	 * Consulta en la base de usuarios si existe el usuario y el password pasados como parametros
	 * @return Devuelve el codigo de usuario si el usuario y password son correctos, y null en caso contrario
	 */
	public Integer login(String usuario, String password) {
		Usuario user = Usuario.buscarPorIdyPassword(usuario, password);
		this.user=user;
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
		return Usuario.buscarPorId(numUsuario);
	}

	private Cliente buscarCliente(int numCliente) {
		return null;
	}

	private Producto buscarProducto(int cod_producto) {
		return null;
	}

	public Collection<ReclamoView> getReclamosSimples() {
		Collection<ReclamoView> reclamosView = new ArrayList<>();
		for (Reclamo reclamo : Reclamo.buscarReclamos()) {
			if(!reclamo.getTipoReclamo().equals("compuesto")){
				ReclamoView reclamoV = new ReclamoView(reclamo.getNumero(), reclamo.getDescripcion(),
						reclamo.getTipoReclamo().getDescripcionTipo(), reclamo.isEstaSolucionado());
				reclamosView.add(reclamoV);
			}
		}
		return reclamosView;
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