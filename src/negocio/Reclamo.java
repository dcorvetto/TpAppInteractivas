package negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import persistencia.AdmPersistenciaReclamo;

public class Reclamo {

	private int numero;
	private Cliente cliente;
	private String descripcion;
	private Usuario operador;
	private List<EventoReclamo> eventos;
	private Usuario responsable;
	private boolean estaSolucionado;
	private double tiempoRespuesta;
	private String tipoReclamo;
	private String zona;

	public Reclamo(int numero, String descripcion, String tipoReclamo) {
		this.numero = numero;
		this.descripcion = descripcion;
		this.tipoReclamo=tipoReclamo;
		this.eventos = new ArrayList<>();
	}
	
	public Reclamo() {
		this.eventos = new ArrayList<>();
	}
	
	public Reclamo(int numero, String desc) {
		this.numero=numero;
		descripcion=desc;
	}

	/**
	* Cantidad de reclamos en el mes pasado por parametro sobre el a�o actual
	* */
	public int getCantReclamosMensual(int mes) {
		Date anioActual = new Date();
		int cant = 0 ;
		for (EventoReclamo evento : eventos) {
			if((anioActual.getYear() == evento.getFecha().getYear()) && (evento.getFecha().getMonth() == mes)){
				cant++;
			}
		}
		return cant;
	}

	public double getTiempoRespuesta() {
		return tiempoRespuesta;
	}
	public static Collection<Reclamo> buscarReclamos(){
		return AdmPersistenciaReclamo.getInstancia().buscarReclamos();
	}

	public void generarEvento(Date fecha, EnumEstado estado, String detalle) {
		EventoReclamo er = new EventoReclamo();
		er.setDetalle(detalle);
		er.setEstado(estado);
		er.setFecha(fecha);
		AdmPersistenciaReclamo.getInstancia().insertEventoReclamo(this.getNumero(), er);
	}

	public void guardarCambios() {
		AdmPersistenciaReclamo reclamoMapper=AdmPersistenciaReclamo.getInstancia();
		reclamoMapper.insert(this);		
	}

	public void agregarDetalle(Date fecha, String detalle) {
		eventos.add(new EventoReclamo(fecha, detalle));
	}

	public void cambiarEstado(Date fecha, EnumEstado estado, String detalle) {
		eventos.add(new EventoReclamo(estado, fecha, detalle));
	}

	public void cambiarEstado(Date fecha, EnumEstado estado) {
		eventos.add(new EventoReclamo(estado, fecha));
	}
	
	
	//Getters y setters
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getOperador() {
		return operador;
	}

	public void setOperador(Usuario operador) {
		this.operador = operador;
	}

	public Collection<EventoReclamo> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoReclamo> eventos) {
		this.eventos = eventos;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public boolean isEstaSolucionado() {
		return estaSolucionado;
	}

	public void setEstaSolucionado(boolean estaSolucionado) {
		this.estaSolucionado = estaSolucionado;
	}

	public void setTiempoRespuesta(double tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}
	
	public String getTipoReclamo() {
		return tipoReclamo;
	}

	public void setTipoReclamo(String tipoReclamo) {
		this.tipoReclamo = tipoReclamo;
	}
	
	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	
}