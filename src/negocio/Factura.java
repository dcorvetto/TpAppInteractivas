package negocio;

import java.util.Date;

public class Factura {
	private int idFactura;
	private Date fecha;
	private Cliente cliente;
	
	
	public Factura() {
	}
	
	public Factura(int idFactura, Date fecha, Cliente cliente) {
		this.setIdFactura(idFactura);
		this.setFecha(fecha);
		this.setCliente(cliente);
	}
	
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
