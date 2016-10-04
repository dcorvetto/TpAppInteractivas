package negocio.reclamos;

import negocio.Factura;

import java.util.Date;

public class ItemFacturaReclamo {
	private Date fechaFactura;
	private Factura factura;

	public ItemFacturaReclamo(Date fechaFactura, Factura factura) {
		this.fechaFactura = fechaFactura;
		this.factura = factura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
}
