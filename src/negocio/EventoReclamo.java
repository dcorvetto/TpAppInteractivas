package negocio;

import java.util.Date;

public class EventoReclamo {
	private EnumEstado estado;
	private Date fecha;
	private String detalle;
	private int idEventoReclamo;
	
	public EventoReclamo(EnumEstado estado, Date fecha, String detalle) {
		this.estado = estado;
		this.fecha = fecha;
		this.detalle = detalle;
	}

	public EventoReclamo(Date fecha, String detalle) {
		this.estado = EnumEstado.INGRESADO;
		this.fecha = fecha;
		this.detalle = detalle;
	}

	public EventoReclamo(EnumEstado estado, Date fecha) {
		this.estado = estado;
		this.fecha = fecha;
	}

	public void actualizarEstado(String estado, String detalle) {
		this.estado = EnumEstado.valueOf(estado.toUpperCase().replace(" ", "_"));
		this.detalle = detalle;
	}

	public Date getFecha() {
		return fecha;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public EventoReclamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdEventoReclamo() {
		return idEventoReclamo;
	}

	public void setIdEventoReclamo(int idEventoReclamo) {
		this.idEventoReclamo = idEventoReclamo;
	}
	
}
