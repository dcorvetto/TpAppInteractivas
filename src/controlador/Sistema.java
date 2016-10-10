package controlador;

import negocio.*;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoProducto;
import negocio.views.ClienteView;
import negocio.views.EventoReclamoView;
import negocio.views.ReclamoTPromXOperadorView;
import negocio.views.ReclamoView;
import persistencia.AdmPersistenciaCliente;
import persistencia.AdmPersistenciaProducto;
import persistencia.AdmPersistenciaReclamo;

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
			tiposDeReclamos.add("producto");
			tiposDeReclamos.add("faltante");
			tiposDeReclamos.add("cant");
			tiposDeReclamos.add("facturacion");
			tiposDeReclamos.add("zona");
			tiposDeReclamos.add("compuesto");
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
		Cliente c = AdmPersistenciaCliente.getInstancia().buscarCliente(codigo_cliente);
		ReclamoProducto r = new ReclamoProducto();
		r.setCliente(c);
		r.setDescripcion(descripcion);
		r.setEstaSolucionado(false);
		r.setOperador(user);
		//TODO: cambiar el parametro de abajo y pasarle un responsable que tendria que recibir por parametro
		r.setResponsable(user);
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

	public void crearReclamoCantidades(int codigo_cliente, Map<Integer, Integer> mapCodigoCantidad, String descripcion) { // Map<codigo_producto,cantidad>
	}

	public void crearReclamoZona(int codigo_cliente, String zona, String descripcion) {
	}

	public void crearReclamoFactura(int codigo_cliente, String descripcion, Map<Integer, Date> mapIdFecha) { //Map<id_factura,fecha_factura>
	}

	public void crearReclamoFaltantes(int codigo_cliente, int cod_producto, int cant_socilitada, int cant_recibidad, String descripcion) {
	}

	public void crearReclamoCompuesto(int codigo_cliente, List<Integer> ids_reclamos) {
		ReclamoCompuesto rc = new ReclamoCompuesto();
		Cliente cc = AdmPersistenciaCliente.getInstancia().buscarCliente(1);
		Usuario operadorc = user;
		//TODO: cambiar linea de abajo para agregar responsable que vendra por parametro
		Usuario respc = user;
		
		List<Reclamo> listaReclamos = new ArrayList<Reclamo>();
		for(int i=0; i<ids_reclamos.size();i++){
			listaReclamos.add(AdmPersistenciaReclamo.getInstancia().buscarReclamo(ids_reclamos.get(i)));
		}	
		rc.setCliente(cc);
		rc.setDescripcion("Reclamo compuesto");
		rc.setEstaSolucionado(false);
		rc.setOperador(operadorc);
		rc.setResponsable(respc);
		rc.setTiempoRespuesta(-1f);
		rc.setTipoReclamo("compuesto");
		rc.setZona(null);
		rc.setReclamos(listaReclamos);
		
		AdmPersistenciaReclamo.getInstancia().insert(rc);
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
		Usuario user = Usuario.buscarUsuario(usuario, password);
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
		Usuario usuario = new Usuario();
		return usuario.buscarUsuario(numUsuario);
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
						reclamo.getTipoReclamo(), reclamo.isEstaSolucionado());
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