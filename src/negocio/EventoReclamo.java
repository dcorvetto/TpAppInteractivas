package negocio;

import java.util.Date;

public class EventoReclamo {
	private EnumEstado estado;
	private Date fecha;
	private String detalle;

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
}
