package negocio.reclamos;

import negocio.Factura;

public class ItemFacturaReclamo {
	private long fechaFactura;
	private Factura factura;

	public ItemFacturaReclamo() {
	}

	public ItemFacturaReclamo(long fechaFactura, Factura factura) {
		this.fechaFactura = fechaFactura;
		this.factura = factura;
	}

	public long getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(long fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
}
