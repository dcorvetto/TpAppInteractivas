package negocio.views;

import negocio.EnumEstado;

import java.util.Date;

public class EventoReclamoView {
    private EnumEstado estado;
    private Date fecha;
    private String detalle;

    public EventoReclamoView(EnumEstado estado, Date fecha, String detalle) {
        this.estado = estado;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public EventoReclamoView() {
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
